function convergence=Diff(alphePast,alphe,xiPast,xi,BPast,B)

convergence=0;
convergence=convergence+norm((alphePast-alphe),2)+norm((xiPast-xi),2)+norm((BPast-B),2);

end