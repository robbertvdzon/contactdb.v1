FROM ubuntu:14.04
MAINTAINER Robbert vd Zon <robbert@vdzon.com>

# install package building helpers
RUN apt-get -y update && apt-get -y --force-yes install pure-ftpd

# setup ftpgroup and ftpuser
RUN groupadd ftpgroup
RUN useradd -g ftpgroup -d /dev/null -s /etc ftpuser

# startup
CMD /usr/sbin/pure-ftpd -l puredb:/etc/pure-ftpd/pureftpd.pdb -x -E -j -R

EXPOSE 21/tcp