--
-- In Oracle 10g from the EMP demonstration table
--

-- display empno and ename from EMP table
select empno, ename from emp;

-- drop and create types and procedure
DROP PROCEDURE pr_get_emp_array;
DROP TYPE emp_tbl;
DROP TYPE emp_obj;

CREATE TYPE emp_obj IS OBJECT (
   empno   NUMBER (4),
   ename   VARCHAR2 (10)
);
/

CREATE TYPE emp_tbl IS TABLE OF emp_obj;
/

CREATE OR REPLACE PROCEDURE pr_get_emp_array (emp_array OUT emp_tbl)
IS
BEGIN
   emp_array := emp_tbl ();
   FOR rec IN (SELECT empno, ename
                 FROM emp)
   LOOP
      emp_array.EXTEND;
      emp_array (emp_array.LAST) := emp_obj (rec.empno, rec.ename);
   END LOOP;
END;
/

--
-- procedure call from PL/SQL block
--
DECLARE
   arr_emp   emp_tbl;
BEGIN
   pr_get_emp_array (arr_emp);
   DBMS_OUTPUT.put_line (CHR (0));
   DBMS_OUTPUT.put_line (RPAD ('EMPNO', 6) || '  ' || 'ENAME');
   DBMS_OUTPUT.put_line (RPAD ('-', 6, '-') || '  ' || RPAD ('-', 20, '-'));
   FOR i IN 1 .. arr_emp.COUNT
   LOOP
      DBMS_OUTPUT.put_line (RPAD (arr_emp (i).empno, 6) || '  ' || arr_emp (i).ename);
   END LOOP;
END;
/
