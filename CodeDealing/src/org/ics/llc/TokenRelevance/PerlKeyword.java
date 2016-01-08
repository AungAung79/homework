package org.ics.llc.TokenRelevance;

import java.io.PrintWriter;
import java.util.HashSet;

public class PerlKeyword {
	HashSet<String> keyword = new HashSet<String>();
	
	public PerlKeyword()
	{
		String word = "getpwent getservent quotemeta msgrcv scalar kill dbmclose undef lc "
				+"ma syswrite tr send umask sysopen shmwrite vec qx utime local oct semctl localtime "
				+"readpipe do return format read sprintf dbmopen pop getpgrp not getpwnam rewinddir qq "
				+"fileno qw endprotoent wait sethostent bless s|0 opendir continue each sleep endgrent "
				+"shutdown dump chomp connect getsockname die socketpair close flock exists index shmget "
				+"sub for endpwent redo lstat msgctl setpgrp abs exit select print ref gethostbyaddr "
				+"unshift fcntl syscall goto getnetbyaddr join gmtime symlink semget splice x|0 "
				+"getpeername recv log setsockopt cos last reverse gethostbyname getgrnam study formline "
				+"endhostent times chop length gethostent getnetent pack getprotoent getservbyname rand "
				+"mkdir pos chmod y|0 substr endnetent printf next open msgsnd readdir use unlink "
				+"getsockopt getpriority rindex wantarray hex system getservbyport endservent int chr "
				+"untie rmdir prototype tell listen fork shmread ucfirst setprotoent else sysseek link "
				+"getgrgid shmctl waitpid unpack getnetbyname reset chdir grep split require caller "
				+"lcfirst until warn while values shift telldir getpwuid my getprotobynumber delete and "
				+"sort uc defined srand accept package seekdir getprotobyname semop our rename seek if q|0 "
				+"chroot sysread setpwent no crypt getc chown sqrt write setnetent setpriority foreach "
				+"tie sin msgget map stat getlogin unless elsif truncate exec keys glob tied closedir "
				+"ioctl socket readlink eval xor readline binmode setservent eof ord bind alarm pipe "
				+"atan2 getgrent exp time push setgrent gt lt or ne m|0 break given say state when";
		
		String other = "split return print reverse grep sub";
		
		String[] sp = word.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
		
		sp = other.split(" ");
		for(int i = 0; i < sp.length; i++)
		{
			keyword.add(sp[i].toLowerCase());
		}
	}
	
	public int getRelevance(String s)
	{
		String[] sp = s.split(" ");
		int relevance = 0;
		for(int i = 0; i < sp.length; i++)
		{
			if(keyword.contains(sp[i]))
				relevance++;
		}
		return relevance;
	}
	
	public void printKeyword()
	{
		try {
			PrintWriter pw = new PrintWriter("/Users/jimmy/StackOverflow/parsed-data/Token/perlkeyword.txt");
			for (String s : keyword) {
				pw.println(s);
			}
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
