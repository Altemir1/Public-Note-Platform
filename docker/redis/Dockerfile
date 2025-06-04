FROM opensuse/leap:15.5

# Install dependencies and Redis
RUN zypper --non-interactive refresh && \
    zypper --non-interactive install redis && \
    zypper clean --all

# Expose Redis default port
EXPOSE 6379

# Redis config location in OpenSUSE is usually /etc/redis
COPY redis.conf /etc/redis/redis.conf

# Start Redis with config file
CMD ["redis-server", "/etc/redis/redis.conf"]