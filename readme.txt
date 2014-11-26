Problem chosen was #2

Design thoughts:

1) The ability to classify any given item will definitely need to be much more sophisticated in a production world.
As such, it's an interface so that future implementations may drop in.

2) Taxes vary wildly and change all the time. These too are an interface specifically because they may encapsulate
different application rules and math to achieve their goals. A composite pattern might prove to be applicable here.