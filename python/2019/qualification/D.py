import sys
import random


def run_test():
    N, B, F = map(int, raw_input().split())
    if B == 1:
        test = ''.join(map(lambda i2: '0' if i2 % 2 == 1 else '1', range(N)))
        print test
        sys.stdout.flush()
        ret = raw_input()
        for i, v in enumerate(ret):
            if v != test[i]:
                print i
                sys.stdout.flush()
                ret = raw_input()
                if ret == '1':
                    return
        print i + 1
        sys.stdout.flush()
        ret = raw_input()
        if ret == '1':
            return
        else:
            return

    broken = []
    tested = []
    for i in range(F + 1):
        # i = random.randint(0, N-1)
        # while i in tested:
        #     i = random.randint(0, N-1)
        tested.append(i)
        print ''.join(map(lambda i2: '0' if i2 == i else '1', range(N)))
        sys.stdout.flush()
        ret = raw_input()
        if ret == '-1':
            return
        if '0' not in ret:
            broken.append(str(i))
        elif ret == '0':
            broken += map(str, filter(lambda i2: i2 != i, range(N)))
        if len(broken) == B:
            print ' '.join(broken)
            sys.stdout.flush()
            ret = raw_input()
            if ret == '1':
                return
    print 'blah'
    sys.stdout.flush()


T = int(raw_input())
for t in range(1, T+1):
    run_test()
