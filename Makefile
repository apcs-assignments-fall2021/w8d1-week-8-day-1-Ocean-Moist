default: run

run:
	./gradlew run

clean:
	./gradlew clean

test:
	./gradlew clean test

tests: test

caesartest: 
	./gradlew clean test --tests MyTests.testEncryptCaesar --tests MyTests.testDecryptCaesar

caesarkeytest:
	./gradlew clean test --tests MyTests.testEncryptCaesarKey --tests MyTests.testDecryptCaesarKey

vigeneretest:
	./gradlew clean test --tests MyTests.testEncryptVigenere --tests MyTests.testDecryptVigenere