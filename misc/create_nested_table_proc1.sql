--
drop table t;
drop type tab_str;

-- Create the table with maximum size of VARCHAR2 datatype
-- for the column y.
create table t (x number, y varchar2(4000));

-- create the nested table
create type tab_str as table of varchar2(4000);
/

--
-- Create the procedure that takes in nested table "tab_str" and populates
-- table t. Maximum size per element is 4000 characters, and maximum number
-- of elements is 2147483647 (the upper limit of PLS_INTEGER).
--
create or replace procedure pr_ntable_test (p_tab_str in tab_str)
as
  type num_tab is table of number;
  subscripts_tab  num_tab := num_tab();
begin
  for i in 1..p_tab_str.count loop
    subscripts_tab.extend;
    subscripts_tab(i) := i;
  end loop;
  --
  forall i in 1..p_tab_str.count
    insert into t (x,y) values (subscripts_tab(i),p_tab_str(i));
  commit;
end;
/
