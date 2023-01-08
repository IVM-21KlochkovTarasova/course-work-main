from nginx:1.20

RUN apt-get update -qq && apt-get -y install curl

COPY ./consul-template /usr/bin/consul-template
RUN mkdir -p /consul-template /consul-template/config.d /consul-template/templates && \
    rm /etc/nginx/conf.d/*

ENV CONSUL consul:8500
COPY ./dist/from-to-education /usr/share/nginx/html

CMD /usr/sbin/nginx -c /etc/nginx/nginx.conf && consul-template -consul-addr=$CONSUL -template "/etc/consul-templates/default.conf.ctmpl:/etc/nginx/conf.d/app.conf:/usr/sbin/nginx -s reload"
