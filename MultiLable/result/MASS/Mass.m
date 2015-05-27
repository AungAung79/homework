function [alpheFinal,xiFinal,Bfinal]=Mass(K,L,B,GammaA,GammaS,Mu,max_iter,n,m,T,Y)

%% Initialization
i=0;
alphePast=zeros((n+m)*T,1);
xiPast=zeros((n+m)*T,1);
BPast=B;

%% Iteration
while i<max_iter
    i=i+1;
    disp(strcat('Mass at the ',num2str(i),'th iteration'));
    [alphe,xi]=QPSolver(K,L,B,GammaA,GammaS,Mu,n,m,T,Y);
    alpheTemp=reshape(alphe,n+m,T);
    ftri=K*alpheTemp;
    B=ftri'*L*ftri/trace(ftri'*L*ftri);
    convergence=Diff(alphePast,alphe,xiPast,xi,BPast,B);
    if convergence<(1e-5)
        disp(strcat('end at the ',num2str(i),'th iteration'));
        break;
    end
    alphePast=alphe;
    xiPast=xi;
    BPast=B;
end

%% Prepare for return
if i==max_iter
    warning('reach maximum iteration~~do not converge!!!');
end
alpheFinal=alphe;
xiFinal=xi;
Bfinal=B;
