function [omageFinal,xiFinal]=MLS(X,L,GammaA,GammaS,Mu,max_iter,n,FeaNum,T,Y)

%% Initialization
i=0;
omagePast=zeros(FeaNum*T,1);
xiPast=zeros(FeaNum*T,1);

%% Iteration
while i<max_iter
    i=i+1;
    disp(strcat('MLS at the ',num2str(i),'th iteration'));
    [omage,xi]=QPSolver(X,L,GammaA,GammaS,Mu,n,FeaNum,T,Y);
    convergence=Diff(omagePast,omage,xiPast,xi);
    if convergence<(1e-5)
        disp(strcat('end at the ',num2str(i),'th iteration'));
        break;
    end
    omagePast=omage;
    xiPast=xi;
end

%% Prepare for return
if i==max_iter
    warning('reach maximum iteration~~do not converge!!!');
end
omageFinal=omage;
xiFinal=xi;
