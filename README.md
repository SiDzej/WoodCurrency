==== Builds ====

http://ci.technicraft.cz/job/WoodCurrency/
http://ci.technicraft.cz/job/WoodCurrency/lastSuccessfulBuild/artifact/target/WoodCurrency.jar



==== History - czech language only ====

Už před dávnými časy byl zrušen adminshop na dřevo. Od těch dob byl zaveden pojem "dřevěná krize". Problém se projevoval tak, že někteří hráči masivně prodávali dřevo a adminshop byl řešen jako účet fake hráče, kterému bohužel po nějaké době došli peníze. To se stalo a dwi řekl "Ne!" na doplnění dalších peněz do systému. A tím vznikl nápad na limitovaný adminshop.

(Viz o dřevní krizi http://forum.majncraft.cz/threads/drevena-krize.7971/ a následně i http://forum.majncraft.cz/threads/co-s-drevni-menou-po-prechodu-na-1-7.7701/ )

Bohužel první snahy byly marné a zdlouhavé, až jsem musel řešit jiné problémy IRL. Pluginu se nikdo jiný neujal. Před několika dny jsem proto začal na novo, s novými znalostmi bukkitu, naplněn odhodláním to dotáhnout. Dnes se povedlo a proto vám mohu představit nový plugin.

Plugin funguje na principu neomezeného účtu (nikdo nemusí doplňovat peníze), ale má zavedené limity na prodej. Podporuje prozatím jen dřevo (kmeny), takže na jiném serveru se asi moc neuplatní. Nakupovat lze neomezeně. Normální hráč má dostupné příkazy /drevni top a drevni info.


/drevni top - informace o 10 největších prodejcích dřeva - obdoba /money top akorát veřejná.
/drevni info - informace o aktuálním stavu denního limitu a o celkových prodejích a nákupech.

Moderátoři mají práva resetovat aktuální stav prodaných kusů, resetovat denní limit na výchozí, podívat se na aktuální stav kohokoliv a také udělit ban na využívání adminshopu. (Ban je jen taková třešnička na dortu... ale třeba se bude hodit).

„Jak funguje limit?“ - To bude asi nejčastěji kladená otázka.

Výchozí limit je nastaven na 1024* kusů na den. Pokud prodáte nějaké** dřevo, pak druhý den se limit sníží na polovinu. Takto lze pokračovat až na 32 kusů za den. Minimálně tedy každý den prodáte 32 kusů dřeva - což stačí na zakoupení vozíku nebo jídla. Pokud nebudete jeden den prodávat vůbec, tak se limit začne zase zvyšovat. Samozřejmě čím déle nebudete prodávat tím víc se limit zvýší, maximálně však na výchozí hodnotu 1024* kusů.

*) - aktuální nastavení pluginu - může být později změněno
**) - když předchozí den prodáte jen zlomek limitu, limit se zvýší jako kdybyste nic neprodávali.

„Co to je za kravinu? Proč mi cpete nějaký limit a neřešíte to s lidmi, kteří moc prodávali?“
Dřevní měna je zamýšlena jako nástroj, jak nováčkům pomoci do začátku. Nemá sloužit jako neomezený zdroj financí pro hráče a narušovat tím tak normální obchod se dřevem. Další funkcí je stabilizace měny - dřevo zde má pevně určenou cenu, nehledě na poptávku a nabídku. Poslední funkce je nejméně důležitá (i když to mnozí pokládali jako primární účel) a to dostat nějaké peníze do oběhu (další možnost je hlasování a nový hráči). Naším cílem bylo ošetřit zneužívání systémově. Hlídat kdo kolik prodal a proč byla dříve složitá a problematická činnost.

„Kde tento obchod naleznu?“ 
Obchod je umístěn na Pískovně na promenádě vpravo pod hlavními schody. Je umístěn na nástěnce. :)


Plugin je zatím v betě. Mohou se vyskytnout neočekávané problémy a bugy, které je nutné nahlásit! Zneužití nedokonalosti pluginu budu trestat osobně banem na hodně dlouhou dobu či permanentním banem.
