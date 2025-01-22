-- #############################################################################
-- Name: test_ntt.sql
-- Desc: Oracle SQL script to create object type, nested table type,
--       a procedure using those and a PL/SQL block to test the
--       procedure.
-- #############################################################################

-- (1) Create the object type and its nested table type
DROP TYPE emp_tab;
DROP TYPE emp_obj;

CREATE OR REPLACE TYPE emp_obj AS OBJECT (
    emp_id    number,
    emp_name  varchar2(20)
);
/

CREATE OR REPLACE TYPE emp_tab AS TABLE OF emp_obj;
/


-- (2) Create the table
DROP TABLE employee;

CREATE TABLE employee (
    emp_id    NUMBER,
    emp_name  VARCHAR2(20)
);


-- (3) Create a procedure to load the nested table and insert its contents
--     into the table.
CREATE OR REPLACE PROCEDURE insert_emp
(
    p_emp            emp_tab,
    p_result  OUT    VARCHAR2
)
IS
BEGIN
   INSERT INTO employee
       SELECT * FROM TABLE(p_emp);
   p_result := 'successful!';
EXCEPTION
   WHEN OTHERS THEN
       p_result := 'failure! -' || SQLERRM;
END;
/
SHOW errors

-- (4) Create a PL/SQL block to test the procedure insert_emp
DECLARE
    tab_emp   emp_tab := emp_tab();
    i         NUMBER := 0;
    v_result  VARCHAR2(30);
BEGIN
    -- add a record
    i := i + 1;
    tab_emp.EXTEND;
    tab_emp(i) := emp_obj(1, 'Adrian');

    -- another record
    i := i + 1;
    tab_emp.EXTEND;
    tab_emp(i) := emp_obj(2, 'Beth');

    -- another record
    i := i + 1;
    tab_emp.EXTEND;
    tab_emp(i) := emp_obj(3, 'Chris');

    -- and two more
    i := i + 1;
    tab_emp.EXTEND;
    tab_emp(i) := emp_obj(4, 'David');

    i := i + 1;
    tab_emp.EXTEND;
    tab_emp(i) := emp_obj(5, 'Eric');

    -- invoke procedure
    insert_emp (tab_emp, v_result);
    DBMS_OUTPUT.PUT_LINE (v_result);
END;
/

-- (5) Check the data in the table employee
SELECT * FROM employee;


