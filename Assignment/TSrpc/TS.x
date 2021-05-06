program TSPROG {
	version TSVERS {
		string LogOn(string,string) = 1;
		string Upload(string) = 1;
		string Download(string) = 1;
		string LogOff(string) = 1;
	} = 1;
} = 79;