(
     // Use the path of whatever file is currently executing:
     var currentPath = thisProcess.nowExecutingPath.dirname;

     // Use the path of the class file for the class you're coding
     var filenameSymbol = this.class.filenameSymbol.asString.dirname;

     var programInstalledRoot=File.getcwd;

     postln(currentPath);
     postln(filenameSymbol);
     postln(programInstalledRoot);
     postln(Document.dir);//??
)