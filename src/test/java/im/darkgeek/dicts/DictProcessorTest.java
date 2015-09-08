package im.darkgeek.dicts;

import junit.framework.TestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by justin on 15-9-8.
 */
public class DictProcessorTest
    extends TestCase {

    @Test
    public void testGenerate() {
        String dictXML = "<!DOCTYPE dictionary [\n" +
                "\n" +
                "<!-- I have used ISO 8879 entity names when they exist.\n" +
                "\t Otherwise, I made up a name ending with an underscore. -->\n" +
                "\n" +
                "<!-- Unicode                  0000 - 007F C0 Controls and Basic Latin -->\n" +
                "\t<!ENTITY amp     '&#x26;#x0026;'> <!-- ISOnum, ampersand -->\n" +
                "\t<!ENTITY lt      '&#x26;#x003C;'> <!-- ISOnum, less-than sign -->\n" +
                "\t<!ENTITY gt           '&#x003E;'> <!-- ISOnum, greater-than sign -->\n" +
                "<!-- Unicode                  0080 - 00FF C1 Controls and Latin-1 Supplement -->\n" +
                "\t<!ENTITY nbsp         '&#x00A0;'> <!-- ISOnum, no-break space = non-breaking space -->\n" +
                "\t<!ENTITY pound        '&#x00A3;'> <!-- ISOnum, pound sign -->\n" +
                "\t<!ENTITY sect         '&#x00A7;'> <!-- ISOnum, section sign -->\n" +
                "\t<!ENTITY uml          '&#x00A8;'> <!-- ISOdia, diaeresis = spacing diaeresis -->\n" +
                "\t<!ENTITY deg          '&#x00B0;'> <!-- ISOnum, degree sign -->\n" +
                "\t<!ENTITY para         '&#x00B6;'> <!-- ISOnum, pilcrow sign = paragraph sign -->\n" +
                "\t<!ENTITY middot       '&#x00B7;'> <!-- ? -->\n" +
                "\t<!ENTITY iquest       '&#x00BF;'> <!-- ISOnum, inverted question mark = turned question mark -->\n" +
                "\t<!ENTITY Auml         '&#x00C4;'> <!-- ISOlat1, latin capital letter A with diaeresis -->\n" +
                "\t<!ENTITY AElig        '&#x00C6;'> <!-- ISOlat1, latin capital letter AE = latin capital ligature AE -->\n" +
                "\t<!ENTITY Ccedil       '&#x00C7;'> <!-- ISOlat1, latin capital letter C with cedilla -->\n" +
                "\t<!ENTITY Eacute       '&#x00C9;'> <!-- ISOlat1, latin capital letter E with acute -->\n" +
                "\t<!ENTITY ETH          '&#x00D0;'> <!-- ISOlat1, latin capital letter ETH -->\n" +
                "\t<!ENTITY times        '&#x00D7;'> <!-- ISOnum, multiplication sign -->\n" +
                "\t<!ENTITY Uuml         '&#x00DC;'> <!-- ISOlat1, latin capital letter U with diaeresis -->\n" +
                "\t<!ENTITY agrave       '&#x00E0;'> <!-- ISOlat1, latin small letter a with grave = latin small letter a grave -->\n" +
                "\t<!ENTITY aacute       '&#x00E1;'> <!-- ISOlat1, latin small letter a with acute -->\n" +
                "\t<!ENTITY acirc        '&#x00E2;'> <!-- ISOlat1, latin small letter a with circumflex -->\n" +
                "\t<!ENTITY atilde       '&#x00E3;'> <!-- ISOlat1, latin small letter a with tilde -->\n" +
                "\t<!ENTITY auml         '&#x00E4;'> <!-- ISOlat1, latin small letter a with diaeresis -->\n" +
                "\t<!ENTITY aring        '&#x00E5;'> <!-- ISOlat1, latin small letter a with ring above = latin small letter a ring -->\n" +
                "\t<!ENTITY aelig        '&#x00E6;'> <!-- ISOlat1, latin small letter ae = latin small ligature ae -->\n" +
                "\t<!ENTITY ccedil       '&#x00E7;'> <!-- ISOlat1, latin small letter c with cedilla -->\n" +
                "\t<!ENTITY egrave       '&#x00E8;'> <!-- ISOlat1, latin small letter e with grave -->\n" +
                "\t<!ENTITY eacute       '&#x00E9;'> <!-- ISOlat1, latin small letter e with acute -->\n" +
                "\t<!ENTITY ecirc        '&#x00EA;'> <!-- ISOlat1, latin small letter e with circumflex -->\n" +
                "\t<!ENTITY euml         '&#x00EB;'> <!-- ISOlat1, latin small letter e with diaeresis -->\n" +
                "\t<!ENTITY igrave       '&#x00EC;'> <!-- ISOlat1, latin small letter i with grave -->\n" +
                "\t<!ENTITY iacute       '&#x00ED;'> <!-- ISOlat1, latin small letter i with acute -->\n" +
                "\t<!ENTITY icirc        '&#x00EE;'> <!-- ISOlat1, latin small letter i with circumflex -->\n" +
                "\t<!ENTITY iuml         '&#x00EF;'> <!-- ISOlat1, latin small letter i with diaeresis -->\n" +
                "\t<!ENTITY eth          '&#x00F0;'> <!-- ISOlat1, latin small letter eth -->\n" +
                "\t<!ENTITY ntilde       '&#x00F1;'> <!-- ISOlat1, latin small letter n with tilde -->\n" +
                "\t<!ENTITY ograve       '&#x00F2;'> <!-- ISOlat1, latin small letter o with grave -->\n" +
                "\t<!ENTITY oacute       '&#x00F3;'> <!-- ISOlat1, latin small letter o with acute -->\n" +
                "\t<!ENTITY ocirc        '&#x00F4;'> <!-- ISOlat1, latin small letter o with circumflex -->\n" +
                "\t<!ENTITY ouml         '&#x00F6;'> <!-- ISOlat1, latin small letter o with diaeresis -->\n" +
                "\t<!ENTITY divide       '&#x00F7;'> <!-- ISOnum, division sign -->\n" +
                "\t<!ENTITY ugrave       '&#x00F9;'> <!-- ISOlat1, latin small letter u with grave -->\n" +
                "\t<!ENTITY uacute       '&#x00FA;'> <!-- ISOlat1, latin small letter u with acute -->\n" +
                "\t<!ENTITY ucirc        '&#x00FB;'> <!-- ISOlat1, latin small letter u with circumflex -->\n" +
                "\t<!ENTITY uuml         '&#x00FC;'> <!-- ISOlat1, latin small letter u with diaeresis -->\n" +
                "\t<!ENTITY thorn        '&#x00FE;'> <!-- ISOlat1, latin small letter thorn with -->\n" +
                "<!-- Unicode                  0100 - 017F Latin Extended-A -->\n" +
                "\t<!ENTITY amacr        '&#x0101;'> <!-- ISOlat2, latin small letter a with macron -->\n" +
                "\t<!ENTITY abreve       '&#x0103;'> <!-- ISOlat2, latin small letter a with breve -->\n" +
                "\t<!ENTITY cacute       '&#x0107;'> <!-- ISOlat2, latin small letter c with acute -->\n" +
                "\t<!ENTITY ccaron       '&#x010D;'> <!-- ISOlat2, latin small letter c with caron -->\n" +
                "\t<!ENTITY emacr        '&#x0113;'> <!-- ISOlat2, latin small letter e with macron -->\n" +
                "\t<!ENTITY ebreve_      '&#x0115;'> <!-- non-SGML, latin small letter e with breve -->\n" +
                "\t<!ENTITY imacr        '&#x012B;'> <!-- ISOlat2, latin small letter i with macron -->\n" +
                "\t<!ENTITY ibreve_      '&#x012D;'> <!-- non-SGML, latin small letter i with breve -->\n" +
                "\t<!ENTITY Omacr        '&#x014C;'> <!-- ISOlat2, latin capital letter o with macron -->\n" +
                "\t<!ENTITY omacr        '&#x014D;'> <!-- ISOlat2, latin small letter o with macron -->\n" +
                "\t<!ENTITY obreve_      '&#x014F;'> <!-- non-SGML, latin small letter o with breve -->\n" +
                "\t<!ENTITY OElig        '&#x0152;'> <!-- ISOlat2, latin capital ligature OE -->\n" +
                "\t<!ENTITY oelig        '&#x0153;'> <!-- ISOlat2, latin small ligature oe -->\n" +
                "\t<!ENTITY umacr        '&#x016B;'> <!-- ISOlat2, latin small letter u with macron -->\n" +
                "\t<!ENTITY ubreve       '&#x016D;'> <!-- ISOlat2, latin small letter u with breve -->\n" +
                "\t<!ENTITY uring        '&#x016F;'> <!-- ISOlat2, latin small letter u with ring above -->\n" +
                "\t<!ENTITY zdot         '&#x017C;'> <!-- ISOlat2, latin small letter z with dot above -->\n" +
                "<!-- Unicode                  0180 - 024F Latin Extended-B -->\n" +
                "\t<!ENTITY aemacr_      '&#x01E3;'> <!-- non-SGML, latin small letter ae with macron (ash) * -->\n" +
                "\t<!ENTITY yogh_        '&#x021D;'> <!-- non-SGML, latin small letter yogh -->\n" +
                "\t<!ENTITY adot_        '&#x0227;'> <!-- non-SGML, latin small letter a with dot above -->\n" +
                "\t<!ENTITY ymacr_       '&#x0233;'> <!-- non-SGML, latin small letter y with macron -->\n" +
                "\t<!ENTITY schwa_       '&#x0259;'> <!-- non-SGML, latin small letter schwa -->\n" +
                "<!-- Unicode                  02B0 - 02FF Spacing Modifier Letters -->\n" +
                "\t<!ENTITY asper_       '&#x02BD;'> <!-- non-SGML, modifier letter reversed comma -->\n" +
                "\t<!ENTITY breve        '&#x02D8;'> <!-- ISOdia, breve -->\n" +
                "<!-- Unicode                  0370 - 03FF Greek -->\n" +
                "\t<!ENTITY Alpha        '&#x0391;'> <!-- ISOgrk3, greek capital letter alpha -->\n" +
                "\t<!ENTITY Beta         '&#x0392;'> <!-- ISOgrk3, greek capital letter beta -->\n" +
                "\t<!ENTITY Gamma        '&#x0393;'> <!-- ISOgrk3, greek capital letter gamma -->\n" +
                "\t<!ENTITY Delta        '&#x0394;'> <!-- ISOgrk3, greek capital letter delta -->\n" +
                "\t<!ENTITY Epsilon      '&#x0395;'> <!-- ISOgrk3, greek capital letter epsilon -->\n" +
                "\t<!ENTITY Zeta         '&#x0396;'> <!-- ISOgrk3, greek capital letter zeta -->\n" +
                "\t<!ENTITY Eta          '&#x0397;'> <!-- ISOgrk3, greek capital letter eta -->\n" +
                "\t<!ENTITY Theta        '&#x0398;'> <!-- ISOgrk3, greek capital letter theta -->\n" +
                "\t<!ENTITY Iota         '&#x0399;'> <!-- ISOgrk3, greek capital letter iota -->\n" +
                "\t<!ENTITY Kappa        '&#x039A;'> <!-- ISOgrk3, greek capital letter kappa -->\n" +
                "\t<!ENTITY Lambda       '&#x039B;'> <!-- ISOgrk3, greek capital letter lambda -->\n" +
                "\t<!ENTITY Mu           '&#x039C;'> <!-- ISOgrk3, greek capital letter mu -->\n" +
                "\t<!ENTITY Nu           '&#x039D;'> <!-- ISOgrk3, greek capital letter nu -->\n" +
                "\t<!ENTITY Xi           '&#x039E;'> <!-- ISOgrk3, greek capital letter xi -->\n" +
                "\t<!ENTITY Omicron      '&#x039F;'> <!-- ISOgrk3, greek capital letter omicron -->\n" +
                "\t<!ENTITY Pi           '&#x03A0;'> <!-- ISOgrk3, greek capital letter pi -->\n" +
                "\t<!ENTITY Rho          '&#x03A1;'> <!-- ISOgrk3, greek capital letter rho -->\n" +
                "\t<!ENTITY Sigma        '&#x03A3;'> <!-- ISOgrk3, greek capital letter sigma -->\n" +
                "\t<!ENTITY Tau          '&#x03A4;'> <!-- ISOgrk3, greek capital letter tau -->\n" +
                "\t<!ENTITY Upsilon      '&#x03A5;'> <!-- ISOgrk3, greek capital letter upsilon -->\n" +
                "\t<!ENTITY Phi          '&#x03A6;'> <!-- ISOgrk3, greek capital letter phi -->\n" +
                "\t<!ENTITY Chi          '&#x03A7;'> <!-- ISOgrk3, greek capital letter chi -->\n" +
                "\t<!ENTITY Psi          '&#x03A8;'> <!-- ISOgrk3, greek capital letter psi -->\n" +
                "\t<!ENTITY Omega        '&#x03A9;'> <!-- ISOgrk3, greek capital letter omega -->\n" +
                "\t<!ENTITY alpha        '&#x03B1;'> <!-- ISOgrk3, greek small letter alpha -->\n" +
                "\t<!ENTITY beta         '&#x03B2;'> <!-- ISOgrk3, greek small letter beta -->\n" +
                "\t<!ENTITY gamma        '&#x03B3;'> <!-- ISOgrk3, greek small letter gamma -->\n" +
                "\t<!ENTITY delta        '&#x03B4;'> <!-- ISOgrk3, greek small letter delta -->\n" +
                "\t<!ENTITY epsilon      '&#x03B5;'> <!-- ISOgrk3, greek small letter epsilon -->\n" +
                "\t<!ENTITY zeta         '&#x03B6;'> <!-- ISOgrk3, greek small letter zeta -->\n" +
                "\t<!ENTITY eta          '&#x03B7;'> <!-- ISOgrk3, greek small letter eta -->\n" +
                "\t<!ENTITY theta        '&#x03B8;'> <!-- ISOgrk3, greek small letter theta -->\n" +
                "\t<!ENTITY iota         '&#x03B9;'> <!-- ISOgrk3, greek small letter iota -->\n" +
                "\t<!ENTITY kappa        '&#x03BA;'> <!-- ISOgrk3, greek small letter kappa -->\n" +
                "\t<!ENTITY lambda       '&#x03BB;'> <!-- ISOgrk3, greek small letter lambda -->\n" +
                "\t<!ENTITY mu           '&#x03BC;'> <!-- ISOgrk3, greek small letter mu -->\n" +
                "\t<!ENTITY nu           '&#x03BD;'> <!-- ISOgrk3, greek small letter nu -->\n" +
                "\t<!ENTITY xi           '&#x03BE;'> <!-- ISOgrk3, greek small letter xi -->\n" +
                "\t<!ENTITY omicron      '&#x03BF;'> <!-- ISOgrk3, greek small letter omicron -->\n" +
                "\t<!ENTITY pi           '&#x03C0;'> <!-- ISOgrk3, greek small letter pi -->\n" +
                "\t<!ENTITY rho          '&#x03C1;'> <!-- ISOgrk3, greek small letter rho -->\n" +
                "\t<!ENTITY sigmav       '&#x03C2;'> <!-- ISOgrk3, greek small ketter final sigma -->\n" +
                "\t<!ENTITY sigma        '&#x03C3;'> <!-- ISOgrk3, greek small letter sigma -->\n" +
                "\t<!ENTITY tau          '&#x03C4;'> <!-- ISOgrk3, greek small letter tau -->\n" +
                "\t<!ENTITY upsilon      '&#x03C5;'> <!-- ISOgrk3, greek small letter upsilon -->\n" +
                "\t<!ENTITY phi          '&#x03C6;'> <!-- ISOgrk3, greek small letter phi -->\n" +
                "\t<!ENTITY chi          '&#x03C7;'> <!-- ISOgrk3, greek small letter chi -->\n" +
                "\t<!ENTITY psi          '&#x03C8;'> <!-- ISOgrk3, greek small letter psi -->\n" +
                "\t<!ENTITY omega        '&#x03C9;'> <!-- ISOgrk3, greek small letter omega -->\n" +
                "\t<!ENTITY digamma_     '&#x03DD;'> <!-- non-SGML, greek small letter digamma -->\n" +
                "<!-- Unicode                  1E00 - 1EFF Latin Extended Additional -->\n" +
                "\t<!ENTITY dsdot_       '&#x1E0D;'> <!-- non-SGML, latin small letter d with dot below -->\n" +
                "\t<!ENTITY hsdot_       '&#x1E25;'> <!-- non-SGML, latin small letter h with dot below -->\n" +
                "\t<!ENTITY lsdot_       '&#x1E37;'> <!-- non-SGML, latin small letter l with dot below -->\n" +
                "\t<!ENTITY mdot_        '&#x1E41;'> <!-- non-SGML, latin small letter m with dot above -->\n" +
                "\t<!ENTITY msdot_       '&#x1E43;'> <!-- non-SGML, latin small letter m with dot below -->\n" +
                "\t<!ENTITY ndot_        '&#x1E45;'> <!-- non-SGML, latin small letter n with dot above -->\n" +
                "\t<!ENTITY nsdot_       '&#x1E47;'> <!-- non-SGML, latin small letter n with dot below -->\n" +
                "\t<!ENTITY nsmacr_      '&#x1E49;'> <!-- non-SGML, latin small letter n with line below -->\n" +
                "\t<!ENTITY rsdot_       '&#x1E5B;'> <!-- non-SGML, latin small letter r with dot below -->\n" +
                "\t<!ENTITY tsdot_       '&#x1E6D;'> <!-- non-SGML, latin small letter t with dot below -->\n" +
                "\t<!ENTITY usuml_       '&#x1E73;'> <!-- non-SGML, latin small letter u with diaeresis below -->\n" +
                "\t<!ENTITY zsdot_       '&#x1E93;'> <!-- non-SGML, latin small letter z with dot below -->\n" +
                "\t<!ENTITY etilde_      '&#x1EBD;'> <!-- non-SGML, latin small letter e with tilde -->\n" +
                "\t<!ENTITY usdot_       '&#x1EE5;'> <!-- non-SGML, latin small letter u with dot below -->\n" +
                "<!-- Unicode                  2000 - 206F General Punctuation -->\n" +
                "\t<!ENTITY mdash        '&#x2014;'> <!-- ISOpub, em dash -->\n" +
                "\t<!ENTITY Verbar       '&#x2016;'> <!-- ISOtech, double vertical line -->\n" +
                "\t<!ENTITY lsquo        '&#x2018;'> <!-- ISOnum, left single quotation mark -->\n" +
                "\t<!ENTITY rsquo        '&#x2019;'> <!-- ? -->\n" +
                "\t<!ENTITY ldquo        '&#x201C;'> <!-- ISOnum, left double quotation mark -->\n" +
                "\t<!ENTITY rdquo        '&#x201D;'> <!-- ISOnum, right double quotation mark -->\n" +
                "\t<!ENTITY dagger       '&#x2020;'> <!-- ISOpub, dagger -->\n" +
                "\t<!ENTITY Dagger       '&#x2021;'> <!-- ISOpub, double dagger -->\n" +
                "\t<!ENTITY bull         '&#x2022;'> <!-- ISOpub, bullet = black small circle -->\n" +
                "\t<!ENTITY prime        '&#x2032;'> <!-- ISOtech, prime = minutes = feet -->\n" +
                "\t<!ENTITY bprime_      '<b>&#x2032;</b>'>\n" +
                "\t<!ENTITY Prime        '&#x2033;'> <!-- ISOtech, double prime = seconds = inches -->\n" +
                "\t<!ENTITY asterism_    '&#x2042;'> <!-- non-SGML, asterism -->\n" +
                "<!-- Unicode                  2100 - 214F Lettetlike Symbols -->\n" +
                "        <!ENTITY ounce_       '&#x2125;'> <!-- ? -->\n" +
                "<!-- Unicode                  2190 - 21FF Mathematical Operators -->\n" +
                "\t<!ENTITY rarr         '&#x2192;'> <!-- ISOnum -->\n" +
                "<!-- Unicode                  2200 - 22FF Mathematical Operators -->\n" +
                "\t<!ENTITY nabla        '&#x2207;'> <!-- ISOtech, nabla = backward difference -->\n" +
                "\t<!ENTITY radic        '&#x221A;'> <!-- ISOtech, square root = radical sign -->\n" +
                "\t<!ENTITY cuberoot_    '&#x221B;'> <!-- non-SGML, cube root -->\n" +
                "\t<!ENTITY int          '&#x222B;'> <!-- ISOtech, integral -->\n" +
                "<!-- Unicode                  2300 - 23FF Miscellaneous Technical -->\n" +
                "\t<!ENTITY frown        '&#x2322;'> <!-- ISOamsr, frown -->\n" +
                "\t<!ENTITY smile        '&#x2323;'> <!-- ISOamsr, smile -->\n" +
                "<!-- Unicode                  2600 - 26FF Miscellaneous Symbols -->\n" +
                "\t<!ENTITY sun_         '&#x2609;'> <!-- non-SGML, sun -->\n" +
                "\t<!ENTITY ascendnode_  '&#x260A;'> <!-- non-SGML, ascending node -->\n" +
                "\t<!ENTITY descendnode_ '&#x260B;'> <!-- non-SGML, descending node -->\n" +
                "\t<!ENTITY hand_        '&#x261E;'> <!-- non-SGML, white right pointing index -->\n" +
                "\t<!ENTITY mercury_     '&#x263F;'> <!-- non-SGML, mercury -->\n" +
                "\t<!ENTITY male         '&#x2642;'> <!-- ISOpub, male sign -->\n" +
                "\t<!ENTITY jupiter_     '&#x2643;'> <!-- non-SGML, jupiter -->\n" +
                "\t<!ENTITY aries_       '&#x2648;'> <!-- non-SGML, aries -->\n" +
                "\t<!ENTITY taurus_      '&#x2649;'> <!-- non-SGML, taurus -->\n" +
                "\t<!ENTITY gemini_      '&#x264A;'> <!-- non-SGML, gemini -->\n" +
                "\t<!ENTITY cancer_      '&#x264B;'> <!-- non-SGML, cancer -->\n" +
                "\t<!ENTITY leo_         '&#x264C;'> <!-- non-SGML, leo -->\n" +
                "\t<!ENTITY virgo_       '&#x264D;'> <!-- non-SGML, virgo -->\n" +
                "\t<!ENTITY libra_       '&#x264E;'> <!-- non-SGML, libra -->\n" +
                "\t<!ENTITY scorpius_    '&#x264F;'> <!-- non-SGML, scorpius -->\n" +
                "\t<!ENTITY sagittarius_ '&#x2650;'> <!-- non-SGML, sagittarius -->\n" +
                "\t<!ENTITY capricorn_   '&#x2651;'> <!-- non-SGML, capricorn -->\n" +
                "\t<!ENTITY aquarius_    '&#x2652;'> <!-- non-SGML, aquarius -->\n" +
                "\t<!ENTITY pisces_      '&#x2653;'> <!-- non-SGML, pisces -->\n" +
                "\t<!ENTITY flat         '&#x266D;'> <!-- ISOpub, music flat sign -->\n" +
                "\t<!ENTITY natur        '&#x266E;'> <!-- ISOpub, music natural sign -->\n" +
                "\t<!ENTITY sharp        '&#x266F;'> <!-- ISOpub, music sharp sign -->\n" +
                "<!-- Unicode                  2700 - 27BF Dingbats -->\n" +
                "\t<!ENTITY sext         '&#x2736;'> <!-- ISOpub, six pointed black star -->\n" +
                "<!-- Unicode                  FB00 - FB4F Alphabetic Presentation Forms -->\n" +
                "\t<!ENTITY filig        '&#xFB01;'> <!-- ISOpub, latin small ligature fi -->\n" +
                "\t<!ENTITY fllig        '&#xFB02;'> <!-- ISOpub, laton small ligature fl -->\n" +
                "\t<!ENTITY ffllig       '&#xFB04;'> <!-- ISOpub, latin small ligature ffl -->\n" +
                "<!-- Unicode                  FFF0 - FFFF Specials -->\n" +
                "\t<!ENTITY Crev_        '&#xFFFD;'>\n" +
                "\t<!ENTITY aitalic_     '&#xFFFD;'>\n" +
                "\t<!ENTITY asuml_       '&#xFFFD;'>\n" +
                "\t<!ENTITY auptack_     '&#xFFFD;'>\n" +
                "\t<!ENTITY csdot_       '&#xFFFD;'>\n" +
                "\t<!ENTITY deletemark_  '&#xFFFD;'>\n" +
                "\t<!ENTITY eitalic_     '&#xFFFD;'>\n" +
                "\t<!ENTITY euptack_     '&#xFFFD;'>\n" +
                "\t<!ENTITY iuptack_     '&#xFFFD;'>\n" +
                "\t<!ENTITY ltilde_      '&#xFFFD;'>\n" +
                "\t<!ENTITY mtilde_      '&#xFFFD;'>\n" +
                "\t<!ENTITY ncirc_       '&#xFFFD;'>\n" +
                "\t<!ENTITY nsmallcap_   '&#xFFFD;'>\n" +
                "\t<!ENTITY oobreve_     '&#xFFFD;'>\n" +
                "\t<!ENTITY oomacr_      '&#xFFFD;'>\n" +
                "\t<!ENTITY ouptack_     '&#xFFFD;'>\n" +
                "\t<!ENTITY pause_       '&#xFFFD;'>\n" +
                "\t<!ENTITY thlig_       '&#xFFFD;'>\n" +
                "\t<!ENTITY unr_         '&#xFFFD;'>\n" +
                "\t<!ENTITY uuptack_     '&#xFFFD;'>\n" +
                "\t<!ENTITY withdot_     '&#xFFFD;'>\n" +
                "\t<!ENTITY ybreve_      '&#xFFFD;'>\n" +
                "\t<!ENTITY dotted2_     '&#xFFFD;'>\n" +
                "\t<!ENTITY dotted3_     '&#xFFFD;'>\n" +
                "\n" +
                "\t<!ELEMENT CAPTION   (b)*>\n" +
                "\t<!ELEMENT H1        (#PCDATA)>\n" +
                "\t<!ELEMENT H2        (#PCDATA)>\n" +
                "\t<!ELEMENT I         (#PCDATA)>\n" +
                "\t<!ELEMENT TITLE     (#PCDATA)>\n" +
                "\t<!ELEMENT a         (#PCDATA)>\n" +
                "\t<!ELEMENT ab        (#PCDATA)>\n" +
                "\t<!ELEMENT ab.entry  (ab|ab.full)*>\n" +
                "\t<!ELEMENT ab.full   (#PCDATA|it)*>\n" +
                "\t<!ELEMENT abbr      (#PCDATA|it|sc)*>\n" +
                "\t<!ELEMENT adjf      (#PCDATA)>\n" +
                "\t<!ELEMENT altname   (#PCDATA|asp|class|col|cref|ecol|er|fam|gen|hw|ord|spn|sub|subclass|sup)*>\n" +
                "\t<!ELEMENT altnpluf  (#PCDATA)>\n" +
                "\t<!ELEMENT altsp     (#PCDATA|abbr|as|asp|au|er|ets|ex|grk|hw|it|pluf|plw|pos|pr|sd|wf)*>\n" +
                "\t<!ELEMENT amorph    (#PCDATA|adjf|au|er|ex|it|mark|pos|pr|qex|xex)*>\n" +
                "\t<!ELEMENT ant       (#PCDATA)>\n" +
                "\t<!ELEMENT antiquetype (#PCDATA)>\n" +
                "\t<!ELEMENT as        (#PCDATA|altname|ant|au|chform|city|col|contr|corpn|cref|ecol|er|ex|examp|exp|fam|film|fld|frac|fract|gen|grk|hascons|i|it|mark|mathex|mcol|ord|org|partof|persfn|person|pos|ptcl|qex|sc|sd|sig|spn|styp|stype|subs|universbold|xex)*>\n" +
                "\t<!ELEMENT asp       (#PCDATA|fam|gen|hw)*>\n" +
                "\t<!ELEMENT au        (#PCDATA|br|i|mark)*>\n" +
                "\t<!ELEMENT au.entry  (au|au.see|au.who|au.work)*>\n" +
                "\t<!ELEMENT au.see    (#PCDATA|i)*>\n" +
                "\t<!ELEMENT au.who    (#PCDATA|i)*>\n" +
                "\t<!ELEMENT au.work   (#PCDATA|i)*>\n" +
                "\t<!ELEMENT b         (#PCDATA|br|ex|i|it|plain|xex)*>\n" +
                "\t<!ELEMENT bar       EMPTY>\n" +
                "\t<!ELEMENT bio       (#PCDATA|booki|br|city|country|date|edi|er|it|note|org|persfn|person|pr|publ|sc)*>\n" +
                "\t<!ELEMENT biography (#PCDATA)>\n" +
                "\t<!ELEMENT blacklettertype (#PCDATA)>\n" +
                "\t<!ELEMENT body      (#PCDATA|br|note|p)*>\n" +
                "\t<!ELEMENT bold      (#PCDATA|xex)*>\n" +
                "\t<!ELEMENT boldfacetype (#PCDATA)>\n" +
                "\t<!ELEMENT book      (#PCDATA)>\n" +
                "\t<!ELEMENT booki     (#PCDATA)>\n" +
                "\t<!ELEMENT bourgeoistype (#PCDATA|xex)*>\n" +
                "\t<!ELEMENT boxtype   (#PCDATA)>\n" +
                "\t<!ELEMENT br        EMPTY>\n" +
                "\t<!ELEMENT branchof  (#PCDATA)>\n" +
                "\t<!ELEMENT caption   (#PCDATA|er|note|qex|spn|xex)*>\n" +
                "\t<!ELEMENT cas       (#PCDATA)>\n" +
                "\t<!ELEMENT causedby  (#PCDATA|gen|spn)*>\n" +
                "\t<!ELEMENT causedbyp (spn)*>\n" +
                "\t<!ELEMENT causes    (#PCDATA)>\n" +
                "\t<!ELEMENT cd        (#PCDATA|a|abbr|altname|altsp|ant|as|asp|au|book|br|cas|chform|chname|city|class|cnvto|col|compof|contr|country|cp|cref|ecol|er|ets|etsep|ety|ex|examp|exp|fam|fexp|figref|fld|frac|fract|gen|geog|grk|i|illust|isa|it|itran|mark|mathex|matrix2x5|member|members|note|ord|part|partof|parts|pbr|persfn|person|pluf|pos|pr|prod|prodby|prodmac|ptcl|sc|sd|ship|sig|source|specif|spn|state|styp|stype|subclass|supr|table|trademark|universbold|var|varn|xex|xlati)*>\n" +
                "\t<!ELEMENT cd2       (#PCDATA|au|cd|ex)*>\n" +
                "\t<!ELEMENT centered  (#PCDATA|point16|point18|point26)*>\n" +
                "\t<!ELEMENT chform    (#PCDATA|sups)*>\n" +
                "\t<!ELEMENT chname    (#PCDATA|it)*>\n" +
                "\t<!ELEMENT chreact   (#PCDATA)>\n" +
                "\t<!ELEMENT city      (#PCDATA|etsep)*>\n" +
                "\t<!ELEMENT clarendontype (#PCDATA)>\n" +
                "\t<!ELEMENT class     (#PCDATA|er)*>\n" +
                "\t<!ELEMENT cnvto     (#PCDATA)>\n" +
                "\t<!ELEMENT col       (#PCDATA|b|cd|it|plain)*>\n" +
                "\t<!ELEMENT colbreak  EMPTY>\n" +
                "\t<!ELEMENT colf      (#PCDATA)>\n" +
                "\t<!ELEMENT colheads  (coltitle)*>\n" +
                "\t<!ELEMENT colp      (#PCDATA)>\n" +
                "\t<!ELEMENT colret    EMPTY>\n" +
                "\t<!ELEMENT coltitle  (#PCDATA)>\n" +
                "\t<!ELEMENT column1   (#PCDATA|vertical)*>\n" +
                "\t<!ELEMENT comm      (#PCDATA)>\n" +
                "\t<!ELEMENT comp      (#PCDATA)>\n" +
                "\t<!ELEMENT company   (#PCDATA)>\n" +
                "\t<!ELEMENT compof    (#PCDATA)>\n" +
                "\t<!ELEMENT conjf     (#PCDATA|i)*>\n" +
                "\t<!ELEMENT conseq    (#PCDATA)>\n" +
                "\t<!ELEMENT consof    (#PCDATA)>\n" +
                "\t<!ELEMENT contains  (#PCDATA)>\n" +
                "\t<!ELEMENT contr     (#PCDATA|colf|cref|er|i)*>\n" +
                "\t<!ELEMENT contxt    (#PCDATA)>\n" +
                "\t<!ELEMENT corpn     (#PCDATA|etsep)*>\n" +
                "\t<!ELEMENT corr      (#PCDATA)>\n" +
                "\t<!ELEMENT country   (#PCDATA)>\n" +
                "\t<!ELEMENT cp        (#PCDATA)>\n" +
                "\t<!ELEMENT cref      (#PCDATA|i)*>\n" +
                "\t<!ELEMENT cs        (#PCDATA|altname|altsp|au|bold|br|cd|cd2|chreact|col|cref|def|er|ety|ex|fld|it|mark|mcol|note|pbr|plu|pluf|pos|pr|q|qau|qex|rj|sd|see|sn|source|specif|spn|stype|subclass|table|ver|wordforms|xex)*>\n" +
                "\t<!ELEMENT ct        (#PCDATA)>\n" +
                "\t<!ELEMENT date      (#PCDATA)>\n" +
                "\t<!ELEMENT datey     (#PCDATA)>\n" +
                "\t<!ELEMENT decf      (#PCDATA)>\n" +
                "\t<!ELEMENT def       (#PCDATA|a|abbr|altname|altnpluf|altsp|ant|as|asp|au|b|book|booki|boxtype|br|branchof|causedby|causedbyp|causes|cd|chform|chname|chreact|city|class|cnvto|col|colf|colp|comp|company|compof|contains|contr|corpn|corr|country|cref|date|datey|divof|ecol|er|ets|etsep|ety|ex|examp|exp|fam|film|fld|frac|fract|funct|gen|geog|grk|grp|hascons|haspart|hw|hwf|hypen|i|illu|inv|iref|isa|it|itrans|jour|kingdom|mark|markp|mathex|mcol|member|memberof|members|membof|methodfor|mord|note|ord|org|part|partof|parts|persfn|person|phylum|plu|pluf|plw|pos|pr|prod|prodby|ptcl|publ|q|qex|recipr|river|rj|sansserif|sc|sd|sfield|sig|sim|simto|singf|singw|sn|source|specif|spn|stage|stageof|state|street|styp|stype|sub|subclass|subfam|subord|suborder|subphylum|subs|subtypes|sup|sups|tr|tradename|tran|unit|universbold|usage|usedby|usedfor|uses|var|varn|wordforms|xex|xlati)*>\n" +
                "\t<!ELEMENT def2      (#PCDATA|au|def|ety|fld|hw|mark|plu|pluf|pos|pr|rj|sd|see|singf|specif|xex)*>\n" +
                "\t<!ELEMENT dictionary (body|front)*>\n" +
                "\t<!ELEMENT div0      (H1|H2|ab.entry|au.entry|p)*>\n" +
                "\t<!ELEMENT divof     (org)*>\n" +
                "\t<!ELEMENT ecol      (b)*>\n" +
                "\t<!ELEMENT edi       (#PCDATA)>\n" +
                "\t<!ELEMENT emits     (#PCDATA)>\n" +
                "\t<!ELEMENT englishtype (#PCDATA|sc)*>\n" +
                "\t<!ELEMENT ent       (#PCDATA|sub)*>\n" +
                "\t<!ELEMENT er        (#PCDATA|as|ets|it|person|pos)*>\n" +
                "\t<!ELEMENT ets       (#PCDATA|gen|grk|spn)*>\n" +
                "\t<!ELEMENT etsep     (#PCDATA|plain)*>\n" +
                "\t<!ELEMENT ety       (#PCDATA|altname|as|asp|au|b|booki|city|company|contr|corpn|country|cref|er|ets|etsep|ex|film|frac|fu|gen|grk|it|itran|itrans|org|persfn|person|pluf|plw|pos|pr|publ|sc|sd|sig|sn|spn|title|tr|tran|xex|xlati)*>\n" +
                "\t<!ELEMENT ex        (#PCDATA|plain|subs)*>\n" +
                "\t<!ELEMENT examp     (#PCDATA)>\n" +
                "\t<!ELEMENT exp       (#PCDATA|frac)*>\n" +
                "\t<!ELEMENT extendedtype (#PCDATA)>\n" +
                "\t<!ELEMENT fam       (#PCDATA)>\n" +
                "\t<!ELEMENT fexp      (#PCDATA|exp)*>\n" +
                "\t<!ELEMENT figcap    (#PCDATA)>\n" +
                "\t<!ELEMENT figref    (#PCDATA)>\n" +
                "\t<!ELEMENT figtitle  (#PCDATA)>\n" +
                "\t<!ELEMENT figure    (figtitle)*>\n" +
                "\t<!ELEMENT film      (#PCDATA)>\n" +
                "\t<!ELEMENT fld       (#PCDATA|er|it|xex)*>\n" +
                "\t<!ELEMENT fr        (#PCDATA)>\n" +
                "\t<!ELEMENT frac      (#PCDATA)>\n" +
                "\t<!ELEMENT fract     (#PCDATA|it|mathex)*>\n" +
                "\t<!ELEMENT frenchelzevirtype (#PCDATA)>\n" +
                "\t<!ELEMENT front     (div0)*>\n" +
                "\t<!ELEMENT fu        (#PCDATA)>\n" +
                "\t<!ELEMENT funct     (it|sups)*>\n" +
                "\t<!ELEMENT gen       (#PCDATA|etsep)*>\n" +
                "\t<!ELEMENT geog      (#PCDATA)>\n" +
                "\t<!ELEMENT germantype (point10)*>\n" +
                "\t<!ELEMENT gothictype (#PCDATA)>\n" +
                "\t<!ELEMENT greatprimertype (#PCDATA)>\n" +
                "\t<!ELEMENT grk       (#PCDATA)>\n" +
                "\t<!ELEMENT grp       (#PCDATA)>\n" +
                "\t<!ELEMENT h1        (#PCDATA)>\n" +
                "\t<!ELEMENT h2        (#PCDATA)>\n" +
                "\t<!ELEMENT hascons   (#PCDATA|er)*>\n" +
                "\t<!ELEMENT haspart   (#PCDATA)>\n" +
                "\t<!ELEMENT headrow   (item|mitem)*>\n" +
                "\t<!ELEMENT hw        (#PCDATA|sub|supr)*>\n" +
                "\t<!ELEMENT hwf       (#PCDATA)>\n" +
                "\t<!ELEMENT hypen     (#PCDATA)>\n" +
                "\t<!ELEMENT i         (#PCDATA)>\n" +
                "\t<!ELEMENT illu      (#PCDATA|er|ex|spn)*>\n" +
                "\t<!ELEMENT illust    (#PCDATA|matrix|xex)*>\n" +
                "\t<!ELEMENT img       EMPTY>\n" +
                "\t<!ELEMENT intensi   (#PCDATA)>\n" +
                "\t<!ELEMENT inv       (#PCDATA)>\n" +
                "\t<!ELEMENT iref      (#PCDATA)>\n" +
                "\t<!ELEMENT isa       (#PCDATA|cref|ecol|fam|phylum)*>\n" +
                "\t<!ELEMENT it        (#PCDATA|b|exp)*>\n" +
                "\t<!ELEMENT item      (#PCDATA)>\n" +
                "\t<!ELEMENT itran     (#PCDATA)>\n" +
                "\t<!ELEMENT itrans    (#PCDATA)>\n" +
                "\t<!ELEMENT jour      (#PCDATA)>\n" +
                "\t<!ELEMENT kingdom   (#PCDATA)>\n" +
                "\t<!ELEMENT longprimertype (#PCDATA|it)*>\n" +
                "\t<!ELEMENT mark      (#PCDATA|as|er|ex|i|it|plain|plw|pos|singf|xex)*>\n" +
                "\t<!ELEMENT markp     (#PCDATA)>\n" +
                "\t<!ELEMENT mathex    (#PCDATA|exp|frac|fract|it|ratio|root|sub|subs|vinc)*>\n" +
                "\t<!ELEMENT matrix    (row)*>\n" +
                "\t<!ELEMENT matrix2x5 (row)*>\n" +
                "\t<!ELEMENT mcol      (#PCDATA|altname|au|cd|chform|col|ety|fld|i|it|mark|note|pos|pr|prod|sd|spn|xex)*>\n" +
                "\t<!ELEMENT member    (#PCDATA|spn)*>\n" +
                "\t<!ELEMENT memberof  (org)*>\n" +
                "\t<!ELEMENT members   (#PCDATA)>\n" +
                "\t<!ELEMENT membof    (#PCDATA)>\n" +
                "\t<!ELEMENT method    (#PCDATA|def|sd)*>\n" +
                "\t<!ELEMENT methodfor (#PCDATA)>\n" +
                "\t<!ELEMENT mhw       (#PCDATA|ety|hw|i|it|mark|plu|pluf|pos|pr|vmorph|xex)*>\n" +
                "\t<!ELEMENT miniontype (#PCDATA|it)*>\n" +
                "\t<!ELEMENT mitem     (#PCDATA|colret|item|str)*>\n" +
                "\t<!ELEMENT mord      (#PCDATA|er|ets|pos|xex)*>\n" +
                "\t<!ELEMENT mstypec   (#PCDATA|it|stypec)*>\n" +
                "\t<!ELEMENT mtable    (row)*>\n" +
                "\t<!ELEMENT musfig    (#PCDATA)>\n" +
                "\t<!ELEMENT nmorph    (#PCDATA|decf|er|plu|pos|pr)*>\n" +
                "\t<!ELEMENT nonpareiltype (#PCDATA|ex)*>\n" +
                "\t<!ELEMENT note      (#PCDATA|H1|I|TITLE|a|abbr|altname|altsp|ant|as|asp|au|b|bold|booki|bourgeoistype|br|causedby|cd|centered|chform|chname|chreact|city|class|col|colf|consof|contr|country|cref|ecol|emits|englishtype|er|ets|etsep|ety|ex|examp|exp|extendedtype|fam|figref|fld|frac|fract|gen|geog|germantype|gothictype|grk|h2|hascons|hw|i|img|intensi|inv|iref|isa|it|longprimertype|mark|mathex|matrix|mcol|member|membof|musfig|nonpareiltype|ord|part|partof|parts|pbr|perf|persfn|person|phylum|picatype|pluf|plw|pos|pr|pre|prod|prodby|ptcl|q|qau|qex|rj|sc|sd|see|sig|singw|smpicatype|sn|source|spn|stage|state|styp|stype|suborder|subphylum|subs|subtypes|sup|table|title|tt|unit|universbold|uses|varn|xex)*>\n" +
                "\t<!ELEMENT oldenglishtype (#PCDATA)>\n" +
                "\t<!ELEMENT oldstyletype (#PCDATA)>\n" +
                "\t<!ELEMENT ord       (#PCDATA)>\n" +
                "\t<!ELEMENT org       (#PCDATA)>\n" +
                "\t<!ELEMENT p         (#PCDATA|abbr|altname|altsp|amorph|ant|as|au|b|bio|biography|bold|br|caption|cd|centered|col|colbreak|comm|conseq|contr|contxt|cref|cs|def|def2|ent|er|ets|ety|ex|figcap|figure|fld|fr|fu|gen|grk|h1|hascons|hw|hypen|illu|illust|it|mark|mcol|method|mhw|miniontype|mord|mtable|nmorph|note|pearltype|persfn|person|pl|plu|pluf|plw|pos|pr|pre|q|qau|qex|refs|rj|sd|see|simto|sing|singf|sn|source|specif|stype|subtypes|syn|table|title|usage|ver|vmorph|wn16s|wnote|wns|wordforms|xex)*>\n" +
                "\t<!ELEMENT part      (#PCDATA|er)*>\n" +
                "\t<!ELEMENT partof    (#PCDATA|er)*>\n" +
                "\t<!ELEMENT parts     (#PCDATA)>\n" +
                "\t<!ELEMENT pbr       EMPTY>\n" +
                "\t<!ELEMENT pearltype (#PCDATA|ex)*>\n" +
                "\t<!ELEMENT perf      (#PCDATA)>\n" +
                "\t<!ELEMENT persfn    (#PCDATA|etsep|it|xex)*>\n" +
                "\t<!ELEMENT person    (#PCDATA|altname|b|ets|etsep|ex|it)*>\n" +
                "\t<!ELEMENT phylum    (#PCDATA|er)*>\n" +
                "\t<!ELEMENT picatype  (#PCDATA|it)*>\n" +
                "\t<!ELEMENT pl        (#PCDATA|plw|pr)*>\n" +
                "\t<!ELEMENT plain     (#PCDATA)>\n" +
                "\t<!ELEMENT plu       (#PCDATA|au|col|er|hw|i|it|mark|note|pluf|plw|pos|pr|xex)*>\n" +
                "\t<!ELEMENT pluf      (#PCDATA)>\n" +
                "\t<!ELEMENT plw       (#PCDATA|abbr|it|pr)*>\n" +
                "\t<!ELEMENT point1    (bar)*>\n" +
                "\t<!ELEMENT point1.5  (bar)*>\n" +
                "\t<!ELEMENT point10   (#PCDATA|bar)*>\n" +
                "\t<!ELEMENT point11   (bar)*>\n" +
                "\t<!ELEMENT point12   (bar)*>\n" +
                "\t<!ELEMENT point14   (bar)*>\n" +
                "\t<!ELEMENT point16   (#PCDATA|bar)*>\n" +
                "\t<!ELEMENT point18   (bar|greatprimertype)*>\n" +
                "\t<!ELEMENT point2    (bar)*>\n" +
                "\t<!ELEMENT point2.5  (bar)*>\n" +
                "\t<!ELEMENT point20   (bar)*>\n" +
                "\t<!ELEMENT point26   (#PCDATA)>\n" +
                "\t<!ELEMENT point3    (bar)*>\n" +
                "\t<!ELEMENT point3.5  (bar)*>\n" +
                "\t<!ELEMENT point4    (bar)*>\n" +
                "\t<!ELEMENT point4.5  (bar)*>\n" +
                "\t<!ELEMENT point5    (bar)*>\n" +
                "\t<!ELEMENT point5.5  (bar)*>\n" +
                "\t<!ELEMENT point6    (bar)*>\n" +
                "\t<!ELEMENT point7    (bar)*>\n" +
                "\t<!ELEMENT point8    (bar)*>\n" +
                "\t<!ELEMENT point9    (bar)*>\n" +
                "\t<!ELEMENT pos       (#PCDATA|i|plain)*>\n" +
                "\t<!ELEMENT pr        (#PCDATA|er|i|it|mark|pos|xex)*>\n" +
                "\t<!ELEMENT pre       (#PCDATA|b|br|headrow|row|source|table|tt)*>\n" +
                "\t<!ELEMENT prod      (#PCDATA|col|ecol)*>\n" +
                "\t<!ELEMENT prodby    (#PCDATA|gen|spn)*>\n" +
                "\t<!ELEMENT prodmac   (#PCDATA)>\n" +
                "\t<!ELEMENT ptcl      (#PCDATA)>\n" +
                "\t<!ELEMENT publ      (#PCDATA|it)*>\n" +
                "\t<!ELEMENT q         (#PCDATA|a|altname|au|b|br|chform|city|company|corpn|country|er|ex|examp|fam|frac|gen|grk|h2|i|it|mark|note|ord|org|persfn|person|publ|qau|qex|qpers|qperson|rj|sb|sc|spn|stype|title|xex)*>\n" +
                "\t<!ELEMENT qau       (#PCDATA|book|br|er|i|publ)*>\n" +
                "\t<!ELEMENT qex       (#PCDATA|plain)*>\n" +
                "\t<!ELEMENT qpers     (#PCDATA)>\n" +
                "\t<!ELEMENT qperson   (#PCDATA)>\n" +
                "\t<!ELEMENT ratio     (#PCDATA)>\n" +
                "\t<!ELEMENT recipr    (#PCDATA)>\n" +
                "\t<!ELEMENT ref       (#PCDATA)>\n" +
                "\t<!ELEMENT refs      (#PCDATA|booki|br|person)*>\n" +
                "\t<!ELEMENT river     (#PCDATA)>\n" +
                "\t<!ELEMENT rj        (#PCDATA|au|mark|qau|ver)*>\n" +
                "\t<!ELEMENT root      (#PCDATA|exp)*>\n" +
                "\t<!ELEMENT row       (#PCDATA|antiquetype|blacklettertype|boldfacetype|br|clarendontype|frenchelzevirtype|gothictype|item|oldenglishtype|oldstyletype|scripttype|td|typewritertype)*>\n" +
                "\t<!ELEMENT sansserif (#PCDATA)>\n" +
                "\t<!ELEMENT sb        EMPTY>\n" +
                "\t<!ELEMENT sc        (#PCDATA)>\n" +
                "\t<!ELEMENT scripttype (#PCDATA)>\n" +
                "\t<!ELEMENT sd        (#PCDATA)>\n" +
                "\t<!ELEMENT see       (#PCDATA|ant|cref|er|ex|pos|simto|xex)*>\n" +
                "\t<!ELEMENT sfield    (#PCDATA)>\n" +
                "\t<!ELEMENT ship      (#PCDATA)>\n" +
                "\t<!ELEMENT sig       (#PCDATA|chform|i|it)*>\n" +
                "\t<!ELEMENT sim       (#PCDATA)>\n" +
                "\t<!ELEMENT simto     (#PCDATA|persfn)*>\n" +
                "\t<!ELEMENT sing      (#PCDATA|it|pr|singf|singw)*>\n" +
                "\t<!ELEMENT singf     (#PCDATA)>\n" +
                "\t<!ELEMENT singw     (#PCDATA|it)*>\n" +
                "\t<!ELEMENT smpicatype (#PCDATA|it)*>\n" +
                "\t<!ELEMENT sn        (#PCDATA)>\n" +
                "\t<!ELEMENT source    (#PCDATA)>\n" +
                "\t<!ELEMENT specif    (#PCDATA)>\n" +
                "\t<!ELEMENT spn       (#PCDATA|ets|plain)*>\n" +
                "\t<!ELEMENT stage     (#PCDATA)>\n" +
                "\t<!ELEMENT stageof   (gen)*>\n" +
                "\t<!ELEMENT state     (#PCDATA)>\n" +
                "\t<!ELEMENT str       (td)*>\n" +
                "\t<!ELEMENT street    (#PCDATA)>\n" +
                "\t<!ELEMENT styp      (#PCDATA|class|col|cref|ecol|er|ex|gen|hw|ord|spn)*>\n" +
                "\t<!ELEMENT stype     (#PCDATA|col|colf|ecol|er|ex|gen|it|ord|spn|sub|suborder|subs)*>\n" +
                "\t<!ELEMENT stypec    (#PCDATA)>\n" +
                "\t<!ELEMENT sub       (#PCDATA)>\n" +
                "\t<!ELEMENT subclass  (#PCDATA|er|stype)*>\n" +
                "\t<!ELEMENT subfam    (#PCDATA)>\n" +
                "\t<!ELEMENT subord    (#PCDATA)>\n" +
                "\t<!ELEMENT suborder  (#PCDATA)>\n" +
                "\t<!ELEMENT subphylum (#PCDATA|er)*>\n" +
                "\t<!ELEMENT subs      (#PCDATA|frac)*>\n" +
                "\t<!ELEMENT subtypes  (#PCDATA|cd|col|mcol|mstypec|pbr|sd|spn|stypec|xex)*>\n" +
                "\t<!ELEMENT sup       (#PCDATA)>\n" +
                "\t<!ELEMENT supr      (#PCDATA)>\n" +
                "\t<!ELEMENT sups      (#PCDATA)>\n" +
                "\t<!ELEMENT syn       (#PCDATA|as|au|b|chname|class|er|fam|gen|ord|org|person|pos|sd|spn|subclass|subfam|subord|subs|varn|xex)*>\n" +
                "\t<!ELEMENT table     (#PCDATA|CAPTION|altname|br|caption|centered|colheads|column1|er|frac|it|point1|point1.5|point10|point11|point12|point14|point16|point18|point2|point2.5|point20|point3|point3.5|point4|point4.5|point5|point5.5|point6|point7|point8|point9|pre|row|tabtitle|title|tr|ttitle|xex)*>\n" +
                "\t<!ELEMENT tabtitle  (#PCDATA)>\n" +
                "\t<!ELEMENT td        (#PCDATA|i|pre)*>\n" +
                "\t<!ELEMENT th        (#PCDATA|i)*>\n" +
                "\t<!ELEMENT title     (#PCDATA)>\n" +
                "\t<!ELEMENT tr        (#PCDATA|mitem|str|td|th)*>\n" +
                "\t<!ELEMENT trademark (#PCDATA)>\n" +
                "\t<!ELEMENT tradename (#PCDATA)>\n" +
                "\t<!ELEMENT tran      (#PCDATA)>\n" +
                "\t<!ELEMENT tt        (#PCDATA|b|br|colf|ct|er|it|pbr|sc)*>\n" +
                "\t<!ELEMENT ttitle    (#PCDATA)>\n" +
                "\t<!ELEMENT typewritertype (#PCDATA)>\n" +
                "\t<!ELEMENT uex       (#PCDATA)>\n" +
                "\t<!ELEMENT unit      (#PCDATA)>\n" +
                "\t<!ELEMENT universbold (#PCDATA)>\n" +
                "\t<!ELEMENT usage     (#PCDATA|as|asp|au|br|contr|cref|def|er|ets|ex|it|pbr|person|pos|q|ref|rj|singw|sn|source|stype|uex|xex)*>\n" +
                "\t<!ELEMENT usedby    (#PCDATA|er)*>\n" +
                "\t<!ELEMENT usedfor   (#PCDATA)>\n" +
                "\t<!ELEMENT uses      (#PCDATA)>\n" +
                "\t<!ELEMENT var       (#PCDATA)>\n" +
                "\t<!ELEMENT varn      (#PCDATA)>\n" +
                "\t<!ELEMENT ver       (#PCDATA)>\n" +
                "\t<!ELEMENT vertical  (#PCDATA)>\n" +
                "\t<!ELEMENT vinc      (#PCDATA)>\n" +
                "\t<!ELEMENT vmorph    (#PCDATA|altsp|au|conjf|er|ets|i|it|mark|pos|pr|usage|xex)*>\n" +
                "\t<!ELEMENT wf        (#PCDATA)>\n" +
                "\t<!ELEMENT wn16s     (#PCDATA)>\n" +
                "\t<!ELEMENT wnote     (#PCDATA)>\n" +
                "\t<!ELEMENT wns       (#PCDATA)>\n" +
                "\t<!ELEMENT wordforms (#PCDATA|altsp|au|col|def|er|ety|fld|hw|it|mark|pos|pr|rj|wf)*>\n" +
                "\t<!ELEMENT xex       (#PCDATA|b)*>\n" +
                "\t<!ELEMENT xlati     (#PCDATA)>\n" +
                "\n" +
                "\t<!ATTLIST a\n" +
                "\t\t\t\thref    CDATA #IMPLIED\n" +
                "\t\t\t\tHREF    CDATA #IMPLIED>\n" +
                "\t<!ATTLIST div0\n" +
                "\t\t\t\tname    CDATA #IMPLIED>\n" +
                "\t<!ATTLIST img\n" +
                "\t\t\t\talign   CDATA #IMPLIED\n" +
                "\t\t\t\tsrc     CDATA #IMPLIED>\n" +
                "\t<!ATTLIST table\n" +
                "\t\t\t\tFRAME   CDATA #IMPLIED\n" +
                "\t\t\t\tRULES   CDATA #IMPLIED>\n" +
                "\t<!ATTLIST th\n" +
                "\t\t\t\tCOLSPAN CDATA #IMPLIED>\n" +
                "\n" +
                "\t<!ENTITY gcide_authorities SYSTEM \"gcide_authorities.xml\">\n" +
                "\t<!ENTITY gcide_abbreviations SYSTEM \"gcide_abbreviations.xml\">\n" +
                "\t<!ENTITY gcide_a SYSTEM \"gcide_a.xml\">\n" +
                "\t<!ENTITY gcide_b SYSTEM \"gcide_b.xml\">\n" +
                "\t<!ENTITY gcide_c SYSTEM \"gcide_c.xml\">\n" +
                "\t<!ENTITY gcide_d SYSTEM \"gcide_d.xml\">\n" +
                "\t<!ENTITY gcide_e SYSTEM \"gcide_e.xml\">\n" +
                "\t<!ENTITY gcide_f SYSTEM \"gcide_f.xml\">\n" +
                "\t<!ENTITY gcide_g SYSTEM \"gcide_g.xml\">\n" +
                "\t<!ENTITY gcide_h SYSTEM \"gcide_h.xml\">\n" +
                "\t<!ENTITY gcide_i SYSTEM \"gcide_i.xml\">\n" +
                "\t<!ENTITY gcide_j SYSTEM \"gcide_j.xml\">\n" +
                "\t<!ENTITY gcide_k SYSTEM \"gcide_k.xml\">\n" +
                "\t<!ENTITY gcide_l SYSTEM \"gcide_l.xml\">\n" +
                "\t<!ENTITY gcide_m SYSTEM \"gcide_m.xml\">\n" +
                "\t<!ENTITY gcide_n SYSTEM \"gcide_n.xml\">\n" +
                "\t<!ENTITY gcide_o SYSTEM \"gcide_o.xml\">\n" +
                "\t<!ENTITY gcide_p SYSTEM \"gcide_p.xml\">\n" +
                "\t<!ENTITY gcide_q SYSTEM \"gcide_q.xml\">\n" +
                "\t<!ENTITY gcide_r SYSTEM \"gcide_r.xml\">\n" +
                "\t<!ENTITY gcide_s SYSTEM \"gcide_s.xml\">\n" +
                "\t<!ENTITY gcide_t SYSTEM \"gcide_t.xml\">\n" +
                "\t<!ENTITY gcide_u SYSTEM \"gcide_u.xml\">\n" +
                "\t<!ENTITY gcide_v SYSTEM \"gcide_v.xml\">\n" +
                "\t<!ENTITY gcide_w SYSTEM \"gcide_w.xml\">\n" +
                "\t<!ENTITY gcide_x SYSTEM \"gcide_x.xml\">\n" +
                "\t<!ENTITY gcide_y SYSTEM \"gcide_y.xml\">\n" +
                "\t<!ENTITY gcide_z SYSTEM \"gcide_z.xml\">\n" +
                "]>" +
                "<dictionary><p><ent>Dab</ent><br/>\n" +
                "<hw>Dab</hw> <pr>(d&abreve;b)</pr>, <pos>n.</pos> <ety>[Perh. corrupted fr. <ets>adept</ets>.]</ety> <def>A skillful hand; a dabster; an expert.</def> <mark>[Colloq.]</mark><br/>\n" +
                "[<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><q>One excels at a plan or the titlepage, another works away at the body of the book, and the third is a <qex>dab</qex> at an index.</q> <rj><qau>Goldsmith.</qau></rj><br/>\n" +
                "[<source>1913 Webster</source>]</p>" + "<p><ent>Delight</ent><br/>\n" +
                "<hw>De*light\"</hw>, <pos>v. t.</pos> <vmorph>[<pos>imp. &amp; p. p.</pos> <conjf>Delighted</conjf>; <pos>p. pr. &amp; vb. n.</pos> <conjf>Delighting</conjf>.]</vmorph> <ety>[OE. <ets>deliten</ets>, OF. <ets>delitier</ets>, <ets>deleitier</ets>, F. <ets>d&eacute;lecter</ets>, fr. L. <ets>delectare</ets> to entice away, to delight (sc. by attracting or alluring), intens. of <ets>delicere</ets> to allure, delight; <ets>de-</ets> + <ets>lacere</ets> to entice, allure; cf. <ets>laqueus</ets> a snare. Cf. <er>Delectate</er>, <er>Delicate</er>, <er>Delicious</er>, <er>Dilettante</er>, <er>Elicit</er>, <er>Lace</er>.]</ety> <def>To give delight to; to affect with great pleasure; to please highly; <as>as, a beautiful landscape <ex>delights</ex> the eye; harmony <ex>delights</ex> the ear.</as></def><br/>\n" +
                "[<source>1913 Webster</source>]</p>\n" +
                "\n" +
                "<p><q>Inventions to <qex>delight</qex> the taste.</q> <rj><qau>Shak.</qau></rj><br/>\n" +
                "[<source>1913 Webster</source>]</p></dictionary>";
        DictProcessor dictProcessor = new DictProcessor();
        Callback addBr = new Callback() {
                public int process(Element element) {
                        List elements = element.getParent().elements();
                        elements.add(elements.indexOf(element) + 1, DocumentHelper.createElement("br"));
                        return 1;
                }
        };
        Callback wrapQuoteToAsInDef = new Callback() {
                public int process(Element element) {
                    for (Iterator j = element.elementIterator(); j.hasNext();) {
                        Element defNode = (Element) j.next();
                        if (defNode.getName().equals("as")) {
                            System.out.println(defNode.asXML());
                            List contents = defNode.content();
                            Iterator iterator = contents.iterator();
                            while (iterator.hasNext()) {
                                Node node = (Node) iterator.next();
                                switch (node.getNodeType()) {
                                    case Node.ELEMENT_NODE:
                                        System.out.println("Node type: element node");
                                        System.out.println("Node value: " + node.asXML());
                                        break;
                                    case Node.TEXT_NODE:
                                        System.out.println("Node type: text node");
                                        System.out.println("Node value: " + node.getText());
                                            if (node.getText().startsWith("as, ")) {
                                            StringBuffer sb = new StringBuffer(node.getText());
                                            int insertPoint = sb.indexOf("as, ") + 4;
                                            sb.insert(insertPoint, "\"");
                                            node.setText(sb.toString());
                                        }
                                        break;
                                }
                            }
                            defNode.addText("\"");
                        }
                        DictUtils.convertElement(defNode);
                    }
                    return 1;
                }
        };

        List<DictItem> list = dictProcessor
                                .loadXML(dictXML)
                                .addQuirk("ety", addBr)
                                .addQuirk("pos", addBr)
                                .addQuirk("def", wrapQuoteToAsInDef)
                                .generate();
        for (DictItem item : list) {
            System.out.println(item.toString());
        }
    }
}
