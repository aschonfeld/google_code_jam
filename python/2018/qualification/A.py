def run_test():
    D, P = raw_input().split()
    D = int(D)
    curr_damage = calc_damage(P)
    if curr_damage <= D:
        return 0

    if 'C' not in P:
        return 'IMPOSSIBLE'
    hacks = 0
    while 'CS' in P:
        last_instance_of_hack = P.rfind('CS')
        P = P[:last_instance_of_hack] + 'SC' + P[last_instance_of_hack+2:]
        curr_damage = calc_damage(P)
        hacks += 1
        if curr_damage <= D:
            return hacks
    return "IMPOSSIBLE"


def calc_damage(P):
    strength = 1
    damage = 0
    for v in P:
        if v == 'C':
            strength *= 2
        else:
            damage += strength
    return damage

T = int(raw_input())
for i in range(1, T + 1):
    print("Case #{}: {}".format(i, run_test()))

# with open('A.out', "w+") as f:
#     for i in range(1, int(raw_input()) + 1):
#         f.write("Case #{}: {}\n".format(i, run_test()))
