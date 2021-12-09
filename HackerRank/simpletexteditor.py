if __name__ == "__main__":
    ops_cnt = int(input().strip())
    current = ''
    history = []

    for _ in range(ops_cnt):
        args = input().strip().split(' ')

        if args[0] == '1':
            current += args[1]
            history.append([args[0], len(args[1])])
        elif args[0] == '2':
            deleted = current[-int(args[1]):]
            current = current[:-int(args[1])]
            history.append([args[0], deleted])
        elif args[0] == '3':
            print(current[int(args[1]) - 1])
        elif args[0] == '4':
            undo = history.pop()
            if undo[0] == '1':
                current = current[:-int(undo[1])]
            elif undo[0] == '2':
                current += undo[1]
