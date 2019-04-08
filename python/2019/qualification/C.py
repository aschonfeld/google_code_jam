import sys


def run_test():
    N, L = map(int, raw_input().split())
    cipher = raw_input().split()
    alphabet = set()
    for a, b in zip(cipher[:-1], cipher[1:]):
        for p in find_common_prime_divisors(int(a), int(b), N, alphabet):
            alphabet.add(p)
    for c in cipher:
        missing_cipher = next(find_missing(alphabet, int(c)), None)
        if missing_cipher is not None:
            alphabet.add(missing_cipher)
    alphabet = {v: chr(65 + i) for i, v in enumerate(sorted(alphabet))}
    print_output(cipher, alphabet, L)


def find_missing(alphabet, c):
    for v in alphabet:
        if c % v == 0 and v != c:
            yield c / v


def print_output(cipher, alphabet, L):
    for i, (a, b) in enumerate(zip(cipher[:-1], cipher[1:])):
        a = int(a)
        b = int(b)
        a_primes = (p for p in alphabet if a % p == 0)
        a1 = next(a_primes)
        a2 = next(a_primes)
        b_primes = (p for p in alphabet if b % p == 0)
        b1 = next(b_primes)
        b2 = next(b_primes)

        if a1 == b1:
            if i == 0:
                sys.stdout.write(alphabet[a2])
            sys.stdout.write(alphabet[a1])
            if i == L - 2:
                sys.stdout.write(alphabet[b2] + "\n")
        elif a1 == b2:
            if i == 0:
                sys.stdout.write(alphabet[a2])
            sys.stdout.write(alphabet[a1])
            if i == L - 2:
                sys.stdout.write(alphabet[b1] + "\n")
        elif a2 == b1:
            if i == 0:
                sys.stdout.write(alphabet[a1])
            sys.stdout.write(alphabet[a2])
            if i == L - 2:
                sys.stdout.write(alphabet[b2] + "\n")
        else:
            if i == 0:
                sys.stdout.write(alphabet[a1])
            sys.stdout.write(alphabet[a2])
            if i == L - 2:
                sys.stdout.write(alphabet[b1] + "\n")


def is_prime(n):

    # Corner cases
    if (n <= 1):
        return False
    if (n <= 3):
        return True

    # This is checked so that we can skip
    # middle five numbers in below loop
    if (n % 2 == 0 or n % 3 == 0):
        return False

    i = 5
    while(i * i <= n) :
        if (n % i == 0 or n % (i + 2) == 0):
            return False
        i = i + 6

    return True


def find_common_prime_divisors(a, b, N, primes):
    for i in range(1, min(a, b) + 1):
        if i > N:
            return
        if a % i == b % i == 0:
            if i not in primes and is_prime(i):
                yield i


T = int(raw_input())
for t in range(1, T+1):
    sys.stdout.write("Case #{}: ".format(t))
    run_test()
