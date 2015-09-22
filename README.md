# gcide-converter
Convert GCIDE XML Data To Slob Format (used in [Aard 2](http://aarddict.org/))

## What's this?
`GCIDE`, short for `GNU Collaborative International Dictionary of English`, is a great and open dictionary derived from `Webster's Revised Unabridged Dictionary (1913)`, which is valuable even if many definitions are old from today's perspective. 

The XML format of `GCIDE` data is available on http://www.ibiblio.org/webster/ , the latest version is 0.51. It's our task to produce [slob](https://github.com/itkach/slob) format of `GCIDE`, needed by [Aard 2](http://aarddict.org/), from XML.

Now, we complete this task in this repo.

## Dependencies
* JDK 7+
* Maven 3.0+
* GNU/Linux or *BSD (Because we need `patch` utility)

## Build
* mkdir /tmp/gcide-build && cd /tmp/gcide-build
* git clone https://github.com/darkgeek/gcide-converter
* Download the latest gcide XML data from http://www.ibiblio.org/webster/gcide_xml-0.51.zip , and extract it somewhere (we use `/tmp` here)
* cd /tmp/gcide_xml-0.51/xml_files/ 
* patch < /tmp/gcide-build/gcide-converter/files/patch-gcide-f.xml
* patch < /tmp/gcide-build/gcide-converter/files/patch-gcide.xml
* cd /tmp/gcide-build/gcide-converter && mvn clean package

## Run
