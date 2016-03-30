# Pylongolf
Pylongolf is a new experimental language for golfing on codegolf.stackexchange.com

## Pushing to Stack
Pushing to the stack acts differently that in other languages.
The code `78` pushes 7 and 8 into the stack, however in Pylongolf it pushes 78.

## Commands
The default mathematical symbols like + - * / work on the selected index in the stack and the one before.  
Text between 2 " symbols is also added to the stack.  
`>` - While Statement  
`<` - Close While Statement  
`!` - Exit program  
`.` - Reset the stack 
`@` - Break out of the while statement  
`[` - Convert selected stack item to number  
`_` - Take input  
`'` - Selected the last stack item  
`|` - Split text by a character  
`:` - Assign selected stack item to a variable. (this removes the item from the stack)  
`)` - Print selected stack item  
`~` - Print stack items without spaces  

## Running a program
To run a program enter this command:  
"java -jar pylongolf.jar PROGRAMLOCATION" replacing PROGRAMLOCATION with the program path.
