#!perl -w

use strict;
use DBI;
use DBD::Oracle qw(:ora_types);

my $str = "abcdefghijkl";
#my $dbh = DBI->connect("DBI:Oracle:ora10g","test","test",{RaiseError => 1, AutoCommit => 1});
my $dbh = DBI->connect("DBI:Oracle:ora10g","test","test");
#$dbh->do("begin pr_clob_test(?); end;");
my $sth = $dbh->prepare("begin pr_clob_test(:LOB); end;");
$sth->bind_param(":LOB",$str,{ora_type=>ORA_CLOB});
#$sth->bind_param(":LOB",$str);
$sth->execute();
#$dbh->commit();
#$sth->finish();
$dbh->disconnect();
