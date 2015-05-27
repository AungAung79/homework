function convergence=Diff(alphePast,alphe,xiPast,xi)

convergence=0;
convergence=convergence+norm((alphePast-alphe),2)+norm((xiPast-xi),2);

end