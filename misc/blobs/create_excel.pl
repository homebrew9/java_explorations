#!perl -w
use Win32::OLE;

# use existing instance if Excel is already running
eval {$ex = Win32::OLE->GetActiveObject('Excel.Application')};
die "Excel not installed" if $@;
unless (defined $ex) {
  $ex = Win32::OLE->new('Excel.Application', sub {$_[0]->Quit;}) or die "Oops, cannot start Excel";
}

# get a new workbook
$book = $ex->Workbooks->Add;

# write to a particular cell
$sheet = $book->Worksheets(1);
$sheet->Cells(1,1)->{Value} = "foo";

# write a 2 rows by 3 columns range
$sheet->Range("A8:C9")->{Value} = [[ undef, 'Xyzzy', 'Plugh' ],
                                   [ 42,    'Perl',  3.1415  ]];

# print "XyzzyPerl"
$array = $sheet->Range("A8:C9")->{Value};
for (@$array) {
  for (@$_) {
  print defined($_) ? "$_|" : "<undef>|";
  }
  print "\n";
}

# save and exit
$book->SaveAs( 'test.xls' );
undef $book;
undef $ex;
