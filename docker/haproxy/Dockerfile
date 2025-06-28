FROM opensuse/leap:15.5

# Install HAProxy
RUN zypper refresh && \
    zypper install -y haproxy && \
    zypper clean --all

# Create config directory and copy config
COPY docker/haproxy/haproxy.cfg /etc/haproxy/haproxy.cfg

# Expose the default HAProxy port
EXPOSE 80

# Run HAProxy in foreground mode
CMD ["haproxy", "-f", "/etc/haproxy/haproxy.cfg", "-db"]