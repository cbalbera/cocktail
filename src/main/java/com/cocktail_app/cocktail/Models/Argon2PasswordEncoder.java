package com.cocktail_app.cocktail.Models;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.crypto.argon2.Argon2PasswordEncoder.constantTimeArrayEquals;

public class Argon2PasswordEncoder implements PasswordEncoder {

    private static final int DEFAULT_SALT_LENGTH = 16;
    private static final int DEFAULT_HASH_LENGTH = 32;
    private static final int DEFAULT_PARALLELISM = 1;
    private static final int DEFAULT_MEMORY = 1 << 12;
    private static final int DEFAULT_ITERATIONS = 3;

    private final Log logger = LogFactory.getLog(getClass());

    private final int hashLength;
    private final int parallelism;
    private final int memory;
    private final int iterations;
    private final BytesKeyGenerator saltGenerator;

    public Argon2PasswordEncoder(int saltLength, int hashLength,
                                 int parallelism, int memory, int iterations) {

        this.hashLength = hashLength;
        this.parallelism = parallelism;
        this.memory = memory;
        this.iterations = iterations;

        this.saltGenerator = KeyGenerators.secureRandom(saltLength);
    }

    public Argon2PasswordEncoder() {
        this(DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH,
                DEFAULT_PARALLELISM, DEFAULT_MEMORY, DEFAULT_ITERATIONS);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        byte[] salt = saltGenerator.generateKey();
        byte[] hash = new byte[hashLength];

        Argon2Parameters params = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id).
                withSalt(salt).
                withParallelism(parallelism).
                withMemoryAsKB(memory).
                withIterations(iterations).
                build();
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(params);
        generator.generateBytes(rawPassword.toString().toCharArray(), hash);

        return Argon2EncodingUtils.encode(hash, params);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            logger.warn("password hash is null");
            return false;
        }

        Argon2EncodingUtils.Argon2Hash decoded;

        try {
            decoded = Argon2EncodingUtils.decode(encodedPassword);
        } catch (IllegalArgumentException e) {
            logger.warn("Malformed password hash", e);
            return false;
        }

        byte[] hashBytes = new byte[decoded.getHash().length];

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(decoded.getParameters());
        generator.generateBytes(rawPassword.toString().toCharArray(), hashBytes);

        return constantTimeArrayEquals(decoded.getHash(), hashBytes);
    }

}
