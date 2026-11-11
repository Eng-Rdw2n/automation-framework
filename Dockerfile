# Maven and Java runtime for the container.
FROM maven:3.9.11-eclipse-temurin-21-alpine

# Browser and libraries required for headless Selenium execution.
RUN apk add --no-cache chromium chromium-chromedriver nss freetype harfbuzz ttf-freefont

# Browser paths inside the container.
ENV CHROME_BINARY=/usr/bin/chromium-browser
ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver

WORKDIR /app

COPY pom.xml ./
COPY src ./src
COPY suites ./suites

# Compile the framework and download Maven dependencies during image build.
RUN mvn --batch-mode --quiet -DskipTests package

# Run the configured smoke suite in headless mode.
ENTRYPOINT ["mvn", "--batch-mode", "test", "-Dheadless=true"]
