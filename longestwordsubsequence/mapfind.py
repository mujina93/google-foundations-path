# given a string S and a list of word D, find the
# word (from D) which is a substring (not a consecutive
# subsequence) of S and
# which is the longest among the ones that you can
# find in S

# Solution 2

import time # for execution time

# data
def init():
    S = 'abppplee'
    D = ['able','ale','apple','bale','kangaroo','hallelujahuh']
    return S, D

def clean(S,D):
    # O(len(S) + len(D))

    # filter out words that are too long
    N = len(S)
    fD = list(filter(lambda str: len(str)<=N, D))
    print("Filtered: ", fD)

    # sort the words by lenght, so that you look for the longest before looking for
    # the shortest. If you find one, you know that that is immediately the longest
    # subsequence in S.
    sD = sorted(fD, key=len, reverse=True)
    print("Sorted: ", sD)

    return sD

# build a map of S, a dictionary representing the information
# in the most efficient way possible for our problem
def decompose(S):
    # O(len(S))
    print("Creating a positional map out of "+S+" (alternative representation for the string):")
    map = {}
    for i, letter in enumerate(S):
        if letter in map:
            map[letter].append(i)
        else:
            map[letter] = [i]
    for letter, position_list in map.items():
        print(letter, position_list)
    return map

def mapfind(S,D):
    # O(len(S)+len(D))

    # clean and sort words (longer sooner)
    D = clean(S,D)  # O(len(S)+len(D))

    # map S into a better representation (for fast lookup)
    map = decompose(S) # O(len(S))

    # look if the word is a substring of S by queriend the map
    for word in D:  # O(len(D)*const)
        print("CHECK: "+word)
        found_valid = False
        for j,letter in enumerate(word):
            print(letter)
            index_of_last_letter = -1
            if letter in map:
                where_in_S = map[letter] # positions of 'letter' in S. One or more may be 'valid'.
                print("is present at ",where_in_S)
                found_valid = False
                # look for the first valid letter (the letter must be in S, and it must be
                # it must come after the last valid letter that was found in S)
                for i in where_in_S:
                    if i > index_of_last_letter:
                        index_of_last_letter = i # found a letter which is after the last one (in S)
                        found_valid = True
                        print("valid position found")
                        break   # no need for going on
                if not found_valid: # no valid letter was found
                    print("no valid letter was found. Stop searching for this word.")
                    break # exit the for letter in word loop, i.e. stop considering this word, it's not there
            else:
                print("no such letter in the string")
                break
        if found_valid: # if the last letter of the word was found in S (and it was valid, i.e. it came after the other letters)
            return word # immediately return (since D was ordered, this is the longest one)
    # if nothing was found
    return None

def run():
    S, D = init()             
    res = mapfind(S,D)
    print("Longest substring is: "+res) if res is not None else print("No word found in the string")

# execution
start = time.time()
run()
end = time.time()
print("Execution time: {:f}".format(end-start))