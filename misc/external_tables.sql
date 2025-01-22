--
-- It is necessary to create a directory object in the database before you can
-- create an external table.
--
create directory sample_dir as 'c:\';

--
-- Definition of the external table
--
create table ext_sample (
  client        varchar2(20),
  datasource    varchar2(20),
  attribute1    varchar2(20),
  attribute2    varchar2(20),
  attribute3    varchar2(20),
  attribute4    varchar2(20),
  attribute5    varchar2(20)
)
organization external (
  type oracle_loader
  default directory sample_dir
  access parameters (
    fields terminated by ','
    missing field values are null
  )
  location
  (
     'sample.dat',
     'sample1.dat'
  )
)
reject limit unlimited;

--
-- Regular table for "sample" files
--
create table sample (
  client        varchar2(20),
  datasource    varchar2(20),
  attribute     varchar2(20)
);

--
-- Unpivot data from ext_sample and load into sample table
--
insert into sample (client,datasource,attribute)
with t as (select * from ext_sample)
--
select client,datasource, attribute1 from t union all
select client,datasource, attribute2 from t union all
select client,datasource, attribute3 from t union all
select client,datasource, attribute4 from t union all
select client,datasource, attribute5 from t
order by 1,2,3;
