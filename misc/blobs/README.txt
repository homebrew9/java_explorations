==== README.txt ====
8-Jun-2010:
The goal of this suite of programs is to load BLOB data into an Oracle table
via JDBC. The BLOB data is actually an MS Excel spreadsheet. An Oracle stored
procedure, in a package, will be invoked from the JDBC program. The procedure
will encrypt the BLOB data using DBMS_CRYPTO before loading it into the table.
Benchmark the times taken to load Excel spreadsheets as a function of their
sizes.

The execution would be like so -
1.  Open the Excel spreadsheet.
2.  Read all bytes into Java, convert to BLOB.
3.  Pass the BLOB to Oracle procedure.

We'll need some way to cross-check the data inserted. Any standard ad-hoc query
tool like TOAD or PL/SQL Developer could be used to view the data.

Things to do:
1)  A Perl script to generate csv files of specified columns, rows, and
    token size. After the script does its job, open the csv file in MS Excel
    and save it as an 
2)  An Oracle package that has the following procedures:
    pr_encrypt_load - procedure to encrypt and load a BLOB into the table
    pr_decrypt_all - procedure to decrypt all BLOB rows in the table
    pr_encrypt_all - procedure to encrypt all BLOB rows in the table
3)  Java program to open the Excel spreadsheet, read the bytes, convert to BLOB
    and then pass it to the Oracle packaged procedure.
