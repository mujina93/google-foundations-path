#!/usr/bin/perl

use strict;
use warnings;
use List::Util qw(sum);

my %after;
my @alphabet = ("a".."z");
for my $firstletter (@alphabet){
    for my $secondletter (@alphabet){
        $after{$firstletter}{$secondletter} = 0;
    }
}

while (<>) {
    while ( /(.)(.)/g ) {
        $after{lc $1}{lc $2}++;
    }
}

for my $this (sort keys %after) {
    print "$this ";
    my $total = sum  values %{ $after{$this} };
    for my $that (sort keys %{ $after{$this} } ) {
        my $chance = $after{$this}{$that} / $total;
        printf "%d ", 100 * 100 * $chance;
    }
    print "\n";
}
