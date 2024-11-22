import sys

if len(sys.argv) < 2:
    print("No input provided")
    sys.exit(1)

input_text = sys.argv[1]
words = input_text.split()
lengths = [len(word) for word in words]

# Print the lengths separated by spaces
print(" ".join(map(str, lengths)))
