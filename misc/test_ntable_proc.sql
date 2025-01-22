--
truncate table t;
--
declare
  str_arr  tab_str := tab_str();
begin
  -- add array elements
  for i in 1..10
  loop
    str_arr.extend;
    -- str_arr(i) := dbms_random.string(1,10);
    str_arr(i) := to_number(substr(power(to_char(systimestamp,'ff'),2),4,6)); -- if dbms_random is unavailable - prat, 4/9/2014
  end loop;

  -- display the values in the nested table
  for i in 1..str_arr.count
  loop
    dbms_output.put_line (str_arr(i));
  end loop;

  -- call proc
  pr_ntable_test(str_arr);
end;
/

--
select count(*)
  from t;
