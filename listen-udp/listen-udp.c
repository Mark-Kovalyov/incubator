#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <time.h>
#include <signal.h>
#include <sys/time.h>

#include "listen-udp.h"

#define MAX_UDP_PACKET_SIZE 65507
#define LINEBUF 65536
#define FLUSH_EVERY 16

#define DUMP_UDP_CONTENT

int sockfd = 0;

int is_stopped = 0;

void intHandler(int dummy) {
    fprintf(stderr ,"Interrupt handler!");
    fflush(stdout);
    fflush(stderr);
    close(sockfd);
    signal(SIGINT, SIG_DFL);
}

void get_current_time_ms(struct current_time_rec *ctr) {
  struct timeval currentTime;
  gettimeofday(&currentTime, NULL);
  long seconds      = currentTime.tv_sec;
  long milliseconds = currentTime.tv_usec / 1000;
  struct tm* timeInfo = localtime(&seconds);
  ctr->year     = timeInfo->tm_year + 1900;
  ctr->month    = timeInfo->tm_mon;
  ctr->day      = timeInfo->tm_mday;
  ctr->hours    = timeInfo->tm_hour;
  ctr->minutes  = timeInfo->tm_min;
  ctr->seconds  = timeInfo->tm_sec;
  ctr->mseconds = milliseconds;
}


void byte_array_to_hex(const uint8_t* src, int size, char* hex) {
    int i = 0;
    for (i = 0; i < size; i++) {
        sprintf(hex + i * 2, "%02X", src[i]);
    }
    hex[i * 2 + 1] = 0;
}

int main(int argc, char **argv) {
    if (argc < 3) {
      fprintf(stderr ,"Listen UDP 1.1 as of Jul 15, 2023\n");
      fprintf(stderr ,"Usage :\n    listen-udp [udp-port] [tag]\n\n");
      return 1;
    }
    char *sin_port = argv[1];
    char *tag = argv[2];
    signal(SIGINT, intHandler);
    pid_t pid = getpid();
    fprintf(stderr ,"PID=%d\n", pid);
    fprintf(stderr ,"tag=%s\n", tag);

    struct sockaddr_in server_addr;

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0) {
        perror("Error creating socket");
        exit(1);
    }

    int reuse = 1;

    if (setsockopt(sockfd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0) {
        perror("Error setting socket option");
        exit(1);
    }

    server_addr.sin_family      = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port        = htons(atoi(sin_port));

    if (bind(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        perror("Error binding socket");
        exit(1);
    }

    uint8_t *udp_buffer = (uint8_t*) malloc(MAX_UDP_PACKET_SIZE);
    char    *hexbuf     = (char*) malloc(MAX_UDP_PACKET_SIZE * 2);


    struct sockaddr_in client_addr;
    struct sockaddr sock_addr;
    socklen_t client_len;


    int cnt = 0;
    while (1) {
        cnt++;

        ssize_t recv_len = recvfrom(sockfd,
                                    udp_buffer,
                                    MAX_UDP_PACKET_SIZE,
                                    0,
                                    &sock_addr,
                                    &client_len);

        if (recv_len < 0) {
            //fprintf(stderr ,"Something wrong with recv_len = %zd\n", recv_len);
            //fflush(stderr);
            continue;
        }

        time_t rawtime;
        struct tm * timeinfo;
        struct current_time_rec ctr;
        get_current_time_ms(&ctr);

        struct sockaddr_in *p_sockaddr_in = &sock_addr;

        char *ip = inet_ntoa(p_sockaddr_in -> sin_addr);

        // ts; ip; source_port; tag; [ hexdump ]
        printf("%04d-%02d-%02d %02d:%02d:%02d.%03d;%s;%d;%s",
          ctr.year,
          ctr.month,
          ctr.day,
          ctr.hours,
          ctr.minutes,
          ctr.seconds,
          ctr.mseconds,
          ip,
          p_sockaddr_in -> sin_port,
          tag
        );

        #ifdef DUMP_UDP_CONTENT
          printf(";%zd", recv_len);
          byte_array_to_hex(udp_buffer, recv_len, hexbuf);
          printf(";%s", hexbuf);
        #endif

        printf("\n");

        fflush(stdout);
    }

}
