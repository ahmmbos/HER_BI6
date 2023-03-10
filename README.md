<h1>Resit bioinformatics final exam (course 6)</h1>
<p>Application (with GUI) that reads (.gbff)-files from Genbank and lets the user search
through authors and/or publications. Authors and publications are connected:
the application lets the user "surf" through the authors and publications.</p>
<h3>Instructions application:</h3>
Develop a program that is able to read genbank (flat file (.gbff)) files and read the data in a list.
Make it possible to look per person or per publication which genomes are linked to that entry.
Also make it possible to search for an author in the list so that it is easier to navigate through the major
amount of authors. 
Make it possible to navigate via an author to a publication and vice versa.
Make it possible to select an author or publication to write to a file with all associated data. 
It is not necessary to build a GUI for the interface, a useful command line tool is
enough. 
The tool must be made for Unix machines (linux & mac), windows is not used by the
customer.
<h3>The application in a nutshell:</h3>
1. Read in the files (.gbff-format) one time;<br>
2. Make it possible to search for an author in the list;<br>
3. Make it possible to go to publication via author (and vice versa);<br>
4. Make it possible to select an item and write all associated data from it to a file;<br>
5. Use the correct datastructures;
