#ifndef _LISTEN_UDP_
#define _LISTEN_UDP_

struct current_time_rec {
   int year;
   int month;
   int day;
   int hours;
   int minutes;
   int seconds;
   int mseconds;
};

void get_current_time_ms(struct current_time_rec *ctr);

#endif
