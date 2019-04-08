def run_test():
    N = raw_input()
    N1, N2 = '', ''
    for d in N:
        curr_d = int(d)
        if curr_d == 4:
            curr_n1 = curr_d / 2
            curr_n2 = curr_d - curr_n1
            N1 += str(curr_n1)
            N2 += str(curr_n2)
        else:
            N1 += d
            N2 += '0'
    N2 = N2.lstrip('0')
    return "{} {}".format(N1, N2)


T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
