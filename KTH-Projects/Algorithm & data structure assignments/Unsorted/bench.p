set terminal pdf
set termoption enhanced

set output "bench.pdf"

set title ""

set key left center

set xlabel "n"
    
set ylabel "time in {/Symbol m}s"

plot "bench.dat" u 1:2 w linespoints  title "linear", \
     "bench.dat" u 1:3 w linespoints  title "binary"

