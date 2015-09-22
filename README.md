# gcide-converter
Convert GCIDE XML Data To Slob Format (used in [Aard 2](http://aarddict.org/))

## What's this?
`GCIDE`, short for `GNU Collaborative International Dictionary of English`, is a great and open dictionary derived from `Webster's Revised Unabridged Dictionary (1913)`, which is valuable even if many definitions are old from today's perspective. 

The XML format of `GCIDE` data is available on http://www.ibiblio.org/webster/ , the latest version is **0.51**. It's our task to produce [slob](https://github.com/itkach/slob) format of `GCIDE`, needed by [Aard 2](http://aarddict.org/), from XML.

Now, we complete this task in this repo.

## Dependencies
* JDK 7+
* Maven 3.0+
* GNU/Linux or *BSD (Because we need `patch` utility)

## Build
```Shell
$ mkdir /tmp/gcide-build && cd /tmp/gcide-build
$ git clone https://github.com/darkgeek/gcide-converter
$ wget -c http://www.ibiblio.org/webster/gcide_xml-0.51.zip && unzip gcide_xml-0.51.zip
$ cd /tmp/gcide-build/gcide_xml-0.51/xml_files/ 
$ patch < /tmp/gcide-build/gcide-converter/files/patch-gcide-f.xml
$ patch < /tmp/gcide-build/gcide-converter/files/patch-gcide.xml
$ cd /tmp/gcide-build/gcide-converter && mvn clean package
```

## Run
```Shell
$ cd /tmp/gcide-build/gcide_xml-0.51/xml_files/
$ java -jar /tmp/gcide-build/gcide-converter/target/gcide-converter-1.0-SNAPSHOT.jar # It'll generate a `dict_creator.py` python script
$ # Install and setup a slob working environment by following this guide: https://github.com/itkach/slob/blob/master/README.org
$ python dict_creator.py # It'll produce a file named `gcide.slob` in current directory
```
If you see the file `gcide.slob` in `/tmp/gcide-build/gcide_xml-0.51/xml_files/`, then fine, it's what we need. You could move it to your Aard dictionary directory.

## Troubleshooting
**Q:** Help! I get `GC overhead limit exceeded` error when I run the `gcide-converter-xxx.jar` file!

**A:** Add option `-Xmx` to enlarge the heap space in JVM, like this:
```Shell
java -Xmx1024m -jar /tmp/gcide-build/gcide-converter/target/gcide-converter-1.0-SNAPSHOT.jar
```

## Contribution
Whenever you see a bug or something wrong, don't hesitate to open an issue or send a PR.

## Screenshot
Meta information of `gcide.slob`:
![gcide.slob tag information](http://oi62.tinypic.com/30lfgba.jpg)

Word lookup:
![word lookup in default mode](http://oi58.tinypic.com/20r5xg2.jpg)

Word lookup (night mode):
![word lookup in night mode](http://oi62.tinypic.com/1euhhx.jpg)
