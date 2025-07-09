FROM opensuse/leap:15.5

# Install Java 17 and Maven
RUN zypper --non-interactive refresh && \
    zypper --non-interactive install java-17-openjdk java-17-openjdk-devel maven git tar gzip && \
    zypper clean --all && \
    update-alternatives --install /usr/bin/java java /usr/lib64/jvm/java-17-openjdk/bin/java 2000 && \
    update-alternatives --install /usr/bin/javac javac /usr/lib64/jvm/java-17-openjdk/bin/javac 2000

# Set JAVA_HOME explicitly
ENV JAVA_HOME=/usr/lib64/jvm/java-17-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

# Set working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the app
RUN mvn clean package -DskipTests

# Expose app port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/publicnotes-0.0.1-SNAPSHOT.jar"]