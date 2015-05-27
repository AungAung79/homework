%% STEP 0: Load Data, Set Default Param
clear;
%addpath 'c:\Program Files\mosek\7\toolbox\r2013a';
addpath '/Users/jimmy/mosek/7/toolbox/r2013a';   %for mac
addpath('../data/');
dataname='Arts';
data=['data/' dataname '.mat'];
load(dataname);

[N,T]=size(train_target);
%[m,T]=size(test_target);

U=train_data;
%hatT=[train_target;test_target];

n=N*0.3;
m=N-n;
Y=train_target(1:n,:);

K=U*U';
NormK=sqrt(diag(K));
NormW=repmat(NormK,1,N);
W=K ./ NormW ./ NormW';
W(isnan(W))=0;
%W=zeros(n+m,n+m);
%for i=1:n+m
%    for j=1:n+m
%        Ui=U(i,:);
%        Uj=U(j,:);
%        normi=norm(Ui,2);
%        normj=norm(Uj,2);
%        if(normi==0 || normj==0)
%            W(i,j)=0;
%        else
%            W(i,j)=Ui*Uj'/(normi*normj);
%        end
%    end
%end
D=diag(W*ones(n+m,1));
L=D-W;
B=eye(T,T);

GammaA=0.1;   %set the param
GammaS=0.5;   %set the param
Mu=0.3;       %set the param

max_iter=50;

%% STEP 1: Training
disp('---------Training: ---------');

[alpheFinal,xiFinal,Bfinal]=Mass(K,L,B,GammaA,GammaS,Mu,max_iter,n,m,T,Y);

%% STEP 2: Evaluation
disp('---------Result: ---------');
alpheMatrix=reshape(alpheFinal,n+m,T);
KTest=test_data*U';
result=KTest*alpheMatrix;
