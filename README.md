# TorreTest
A sample application showing the importance of simplistic, fluid UIs.

# Screenshots
![Main activity](/images/home.png)
![Search results](/images/results.png)

# Changes
1. Added simple main layout with a simple search and logo
2. Made the layout more fluid and removed ImageView logo in favor of TextView
3. Made some cosmetic changes to the textbox. The hint is now no longer white, it's light grey and blends in with the whole interface.
4. Attempted to change screenshot in readme, didn't work.
5. Tried using Volley for communication with website, Volley failed as it just wouldn't send over 'application/json' as Content-Type.
So, instead, I switched to Apache HttpCommons library.
6. Added a results list, which takes a round-rect approach to user icons. The results blend perfectly with the background.
Instead of the results being on a separate page, they are on the same page. Now, because Torre servers don't look through and find usernames or names
that match query (atleast the API doesn't, unless I was not told there is a GET or POST command for that) I had to sort and find users on the app. This is
why searching can be slow (or not fluid, at the very least).
