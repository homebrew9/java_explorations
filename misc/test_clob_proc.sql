--
declare
  c_clob  clob;
begin
  -- create clob
--  c_clob := rpad('a',4000,'b');
--  c_clob := c_clob ||','|| rpad('c',4000,'d');
--  c_clob := c_clob ||','|| rpad('e',4000,'f');
  c_clob := 'axxx,pqr,lmno,bcdefg,wxyz';
  -- call proc
  pr_clob_test(c_clob);
end;
/
