FROM ubuntu:22.04 as buildstage

ENV NVM_DIR /usr/local/nvm
ENV NODE_VERSION 18.10
ARG MAVEN_VERSION=3.8.7


RUN mkdir -p /project/server
RUN mkdir -p /project/client

ENV qqy="-qq -y -o=Dpkg::Use-Pty=0 --no-install-recommends --no-install-suggests"

RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get $qqy update > /dev/null \
    && apt-get $qqy install curl wget lsb-release \
    && apt-get $qqy install ca-certificates \
    && apt-get $qqy install openjdk-18-jdk-headless  \
    && apt-get clean  \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p $NVM_DIR
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash \
    && . $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unzipping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY server /project/server
COPY webInterface /project/client
WORKDIR /project/server
RUN mvn clean package

WORKDIR /project/client
RUN . $NVM_DIR/nvm.sh && npm install
RUN . $NVM_DIR/nvm.sh && npm run build


#########
# STAGE #
#########
FROM ubuntu:22.04

ENV NVM_DIR /usr/local/nvm
ENV NODE_VERSION 18.10
ARG MAVEN_VERSION=3.8.7


RUN mkdir -p /project/server/target
RUN mkdir -p /project/client/dist


ENV qqy="-qq -y -o=Dpkg::Use-Pty=0 --no-install-recommends --no-install-suggests"
RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get $qqy update > /dev/null \
    && apt-get $qqy install wget lsb-release curl nginx \
    && apt-get $qqy install dos2unix \
    && apt-get $qqy install ca-certificates \
    && apt-get $qqy install openjdk-18-jre-headless  \
    && apt-get clean  \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir -p $NVM_DIR
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash \
    && . $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && echo "Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  \
  && echo "Unzipping maven" \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  \
  && echo "Cleaning and setting links" \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY --from=buildstage /project/server/target/*jar /project/server/target
COPY --from=buildstage /project/client/dist /project/client/dist

ADD Docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf
#RUN rm -rf /etc/apt/apt.conf.d/90nginx
# Download nginx startup script
RUN wget -O init-deb.sh https://www.linode.com/docs/assets/660-init-deb.sh
# Move the script to the init.d directory & make executable
RUN mv init-deb.sh /etc/init.d/nginx
RUN chmod +x /etc/init.d/nginx
# Add nginx to the system startup
RUN /usr/sbin/update-rc.d -f nginx defaults

EXPOSE 90

ADD Docker/start.sh /
RUN chmod +x /start.sh \
  && dos2unix /start.sh

CMD ["bash", "/start.sh"]
