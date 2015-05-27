function [alphe,xi]=QPSolver(K,L,B,GammaA,GammaS,Mu,n,m,T,Y)

%% Initialization and Set Randomization Seed
alphe0=zeros((n+m)*T,1);
xi0=zeros((n+m)*T,1);
alphe=zeros((n+m)*T,1);
xi=zeros((n+m)*T,1);
convergence=1;

trial=1;
s = RandStream.create('mt19937ar','seed',trial);
RandStream.setGlobalStream(s);

k=0;

R=eye(T,T)*GammaS/T^2+ones(T,T)*Mu*GammaS/T^3;
BTemp=inv(B)*2*GammaA/(n+m)^2/T^2;
%Q=kron(R*2,K)+kron(BTemp,K*L*K);

%% Iteration
while convergence>(1e-5)
    k=k+1;
    disp(strcat('QPsolver at the ',num2str(k),'th iteration'));
    indexperm=randperm(T);
    for i=1:T
       t=indexperm(1,i);
       t=i;
       indexBegin=(n+m)*(t-1)+1;
       indexEnd=(n+m)*t;
       QQP=2*R(t,t)*K+BTemp(t,t)*K*L*K;
       cQP=zeros(1,n+m);
       for j=1:T
           if(j ~= t)
               cQP=cQP+alphe((n+m)*(j-1)+1:(n+m)*j,:)'*(2*R(j,t)*K+BTemp(j,t)*K*L*K);
           end
       end
       cQP=0.5*cQP';
       %QQP=Q(indexBegin:indexEnd,indexBegin:indexEnd);
       %cQP=[Q(1:indexBegin-1,indexBegin:indexEnd);Q(indexEnd+1:(n+m)*T),indexBegin:indexEnd]'*[alphe(1:indexBegin-1,:);alphe(indexEnd+1:(n+m)*T,:)];
       AQP=diag(Y(:,t))*K(1:n,:);
       blcQP=ones(n,1)-xi(indexBegin:indexBegin-1+n,:);
       bucQP=Inf(n,1);
       blxQP=-Inf(n+m,1);
       buxQP=Inf(n+m,1);
       %disp(strcat('QQP dim: ',num2str(size(QQP))));
       %disp(strcat('cQP dim: ',num2str(size(cQP))));
       %disp(strcat('AQP dim: ',num2str(size(AQP))));
       %disp(strcat('blcQP dim: ',num2str(size(blcQP))));
       %disp(strcat('bucQP dim: ',num2str(size(bucQP))));
       %pause;
       res=mskqpopt(QQP,cQP,AQP,blcQP,bucQP,blxQP,buxQP);
       ret=res.sol.itr.xx;
       %disp(strcat('QPresult: ',num2str(ret)));
       %pause;
       alphe(indexBegin:indexEnd,:)=ret;
       xi(indexBegin:indexEnd,:)=[(ones(n,1)-diag(Y(:,t))*K(1:n,:)*alphe(indexBegin:indexEnd,:));zeros(m,1)];
    end
    convergence=Diff(alphe0,alphe,xi0,xi,B,B);
    alphe0=alphe;
    xi0=xi;
end

%% Prepare for return

