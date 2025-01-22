--
-- Create the table with maximum size of VARCHAR2 datatype
-- for the column y.
--
drop table t;
create table t (x number, y varchar2(4000));

--
-- Create the procedure that takes in a CLOB that is essentially a delimited
-- string, and populates table t. Maximum size of a CLOB datatype in Oracle 9i
-- is 4 GB. In 10g, maximum range is 8 TB to 128 TB depending on the database
-- configuration.
--
create or replace procedure pr_clob_test (p_x in clob)
as
  n_index       number := 0;
  v_delimiter   varchar2(1) := ',';
  n_offset      number;
  n_next_offset number;
begin
  n_offset := dbms_lob.instr(p_x,v_delimiter,1,n_index);
  -- loop through the CLOB; tokenize according to the delimiter
  -- and insert each chunk into the table
  while (n_offset is null or n_offset != 0)
  loop
    n_next_offset := dbms_lob.instr(p_x,v_delimiter,1,n_index+1);
    insert into t (x,y) values (n_index+1,
                                dbms_lob.substr(p_x,
                                                case n_next_offset
                                                  when 0
                                                  then dbms_lob.getlength(p_x)+1
                                                  else n_next_offset
                                                end - nvl(n_offset,0) - 1,
                                                nvl(n_offset,0)+1
                                               )
                               );
    n_index := n_index + 1;
    n_offset := dbms_lob.instr(p_x,v_delimiter,1,n_index);
  end loop;
  commit;
end;
/
show errors
