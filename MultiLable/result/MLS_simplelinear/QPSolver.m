function [omage,xi]=QPSolver(X,L,GammaA,GammaS,Mu,n,FeaNum,T,Y)

%% Initialization and Set Randomization Seed
omage0=zeros(FeaNum*T,1);
xi0=zeros(n*T,1);
omage=zeros(FeaNum*T,1);
xi=zeros(n*T,1);
convergence=1;

trial=1;
s = RandStream.create('mt19937ar','seed',trial);
RandStream.setGlobalStream(s);

k=0;

R=eye(T,T)*GammaS/T^2+ones(T,T)*Mu*GammaS/T^3;
BTemp=2*GammaA/n^2/T^2*eye(T,T);
%Q=kron(R*2,K)+kron(BTemp,K*L*K);

%% Iteration
while convergence>(1e-5)
    k=k+1;
    disp(strcat('QPsolver at the ',num2str(k),'th iteration'));
    indexperm=randperm(T);
    for i=1:T
       t=indexperm(1,i);
       t=i;
       indexBegin=FeaNum*(t-1)+1;
       indexEnd=FeaNum*t;
       indexBeginXi=n*(t-1)+1;
       indexEndXi=n*t;
       QQP=2*R(t,t)*eye(FeaNum,FeaNum)+BTemp(t,t)*X'*L*X;
       cQP=zeros(1,FeaNum);
       for j=1:T
           if(j ~= t)
               cQP=cQP+omage(FeaNum*(j-1)+1:FeaNum*j,:)'*(2*R(j,t)*eye(FeaNum,FeaNum)+BTemp(j,t)*X'*L*X);
           end
       end
       cQP=0.5*cQP';
       %QQP=Q(indexBegin:indexEnd,indexBegin:indexEnd);
       %cQP=[Q(1:indexBegin-1,indexBegin:indexEnd);Q(indexEnd+1:(n+m)*T),indexBegin:indexEnd]'*[alphe(1:indexBegin-1,:);alphe(indexEnd+1:(n+m)*T,:)];
       AQP=diag(Y(:,t))*X;
       blcQP=ones(n,1)-xi(indexBeginXi:indexEndXi,:);
       bucQP=Inf(n,1);
       blxQP=-Inf(FeaNum,1);
       buxQP=Inf(FeaNum,1);
       disp(strcat('QQP dim: ',num2str(size(QQP))));
       disp(strcat('cQP dim: ',num2str(size(cQP))));
       disp(strcat('AQP dim: ',num2str(size(AQP))));
       disp(strcat('blcQP dim: ',num2str(size(blcQP))));
       disp(strcat('bucQP dim: ',num2str(size(bucQP))));
       %pause;
       res=mskqpopt(QQP,cQP,AQP,blcQP,bucQP,blxQP,buxQP);
       ret=res.sol.itr.xx;
       %disp(strcat('QPresult: ',num2str(ret)));
       %pause;
       omage(indexBegin:indexEnd,:)=ret;
       disp(strcat('dim: ',num2str(size(diag(Y(:,t))*X*omage(indexBegin:indexEnd,:)))));
       xi(indexBeginXi:indexEndXi,:)=ones(n,1)-diag(Y(:,t))*X*omage(indexBegin:indexEnd,:);
    end
    convergence=Diff(omage0,omage,xi0,xi);
    omage0=omage;
    xi0=xi;
end

%% Prepare for return

