.PHONY: run
run:
	mvn compile exec:java -Dexec.mainClass="ru.university.App" -q

.PHONY: run-clean
run-clean:
	mvn clean compily exec:java -Dexec.mainClass="ru.university.App" -q

.PHONY: dev
dev:
	mvn compile exec:java -Dexec.mainClass="ru.university.App" -Dexec.classpathScope=runtime

.PHONY: test
test:
	mvn test

.PHONY: build
build:
	mvn package

.PHONY: clean
clean:
	mvn clean
