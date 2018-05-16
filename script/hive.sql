create table Wuxia(word STRING, count DOUBLE) 
    row format delimited
    fields terminated by '\t'
    lines terminated by '\n'
    stored as textfile;

load data local inpath 'frequency_statistics.txt' 
    overwrite into table Wuxia;

select * from Wuxia where count>300;

select * from Wuxia order by count desc limit 100;