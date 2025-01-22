--
-- 1) Check that the Java class and associated table are present in Oracle
--
select object_type, object_name, status, created, last_ddl_time
from user_objects
where (object_type like '%JAVA%' or object_name like '%JAVA%')
order by created desc;

--
-- 2) Create a wrapper PL/SQL function for the Java class
--
CREATE OR REPLACE FUNCTION fn_sleep(n_interval number)
RETURN NUMBER
AS LANGUAGE JAVA
NAME 'Utils.sleep(int) return int';
/
SHOW ERRORS

--
-- 3) Test the wrapper PL/SQL function
--
set serveroutput on
declare
    n_result number;
begin
    dbms_output.put_line('SLEEP START : ' || localtimestamp);
    n_result := fn_sleep(10);
    dbms_output.put_line('SLEEP END   : ' || localtimestamp);
end;
/

--
-- 4) Drop all objects if you don't want them
--
drop function fn_sleep;
drop java class "Utils";
drop table "CREATE$JAVA$LOB$TABLE";


