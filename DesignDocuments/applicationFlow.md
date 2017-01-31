# Application Flow


### (A) User Sign up - SignIn Page - request account
1. User chooses to request an account (button on home page in sign-in area)
2. page redirects to account request servlet 
3. servlet redirects to account request jsp
4. User fills out the sign up form and submits.
5. Request goes to sign up servlet.
6. Servlet creates an account request and puts a link to the request in the administrator message queue
7. Servlet redirects to page indicating that the request will be processed by the site administrator

### (B) User Requests credential assistance
1. User selects forgot user name or password
2. Page redirects to credential assistance servlet
3. Servlet redirects to credential assistance page
4. User enters name and selects forgot username, forgot password, or both
5. Request goes to credential request servlet
6. Servlet creates a message for the administrator (or more automated feature if time permits)
7. Servlet redirects back to sign-in page

### (C) User Sign In
1. User enters username and password and selects signIn button
2. Page redirects to signIn servlet
3. SignIn servlet invokes credential verification method 
   JDBCRealm used for authentication (users, users_roles, and roles table).
4. SignIn servlet redirects to either internal home page (IHP) or 
   sign-in page (SIP) with credentials not recognized text on page

### (D) Messaging Flow (creating new message)
1. User selects createMessage button from internal home page
2. Page redirects to createMessage servlet
3. CreateMessage servlet redirects to CreateMessage Page
4. CreateMessage page provides a pull-down or type-in box for a single recipient 
   (administrator may send to all users)
5. CreateMessage page provides medium-sized box for message with 1000 character limit
6. CreateMessage page provides Send, Save, and Cancel buttons
7. Cancel returns user to internal home page (IHP)
8. Send redirects to sendMessage servlet which invokes sendMessage method and then redirects to IHP
9. Save redirects to saveDraft servlet which invokes saveMessage method and then redirects to IHP

### (E) Messaging Flow (starting with existing message)
1. User selects a message from messaging pull-down list on home page
2. Page redirects to readMessage servlet
3. Servlet calls retrieveMessage method and redirects to readMessage page for received messages or
   to createMessage page for drafts
4. ReadMessage page includes a REPLY button
5. Page redirects to createMessage servlet as described in (D)

### (F) Account Creation
1. Administrator logs in following standard login (C)
2. Administrator has access to messages following standard message flow (D)
3. Administrator selects MANAGE button (hidden and disabled for non-administrators)
4. Page redirects to accountManagement servlet
5. Servlet redirects to accountManagement page
6. Administrator fills in user account fields (user name, password, permissions) and selects CREATE
7. Page redirects to createAccount servlet
8. CreateAccount servlet calls createAccount method and redirects to accountManagement page with result

### (G) Administrator Password Reset
1. Administrator selects existing account, a new password, and RESET button
2. Page redirects to password reset servlet
3. Servlet calls password reset method and redirects back to the accountManagement page with result

### (H) User Password Update
1. User signs in (C)
2. User selects PASSWORD_RESET button
3. Page redirects to password servlet
4. Password servlet redirects to password page
5. User enters username and current password and new password twice and selects SUBMIT button
6. Page redirects to newPassword servlet
7. servlet calls updatePassword method which calls credential verification method
8. servlet returns user to IHP with SUCCESS method or password page with ERROR message

### (I) Create new Playlist
1. User selects NEW_PLAYLIST button
2. Page redirects to newPlaylist servlet
3. Servlet redirects to empty editPlaylist page
4. Page provides fields for AddSong option and also delete and move up/move down buttons
5. Addsong redirects to newSong servlet
6. Servlet calls editPlaylist method which adds the song and the playlist 
   and then redirects to non-empty editPlaylist page
   
### (J) Edit existing Playlist
1. User selects a playlist and the EDIT button
2. Page redirects to the editPlaylist servlet
3. EditPlaylist servlet calls retrievePlaylist and redirects to the editPlaylist page
4. EditPlaylist page provides AddSong, delete (x), move, and share options
5. Move buttons call reorderPlaylist servlet
6. reorderPlaylist servlet calls reorderPlaylist method and redirects back to EditPlaylist page
7. AddSong calls editPlaylist method which adds the song and redirects back to EditPlaylist page
8. Delete calls deleteSong method which removes the song and redirects back to EditPlaylist page
9. SHARE calls retrieveUsers servlet and redirects back to EditPlaylist page with pull-down list of users
10. User selects users and selects DONE
11. Page calls sharePlaylist servlet
12. Servlet calls sharePlaylist method and redirects back to IHP

### (K) Play playlist
1. User selects Playlist and may also select shuffle then selects PLAY
2. Page calls playList servlet
3. playList servlet calls playList method and redirects to IHP
4. User has PAUSE and STOP buttons. PAUSE changes to RESUME on selection 
   (should this be external media player?)
5. Page displays the songlist and indicates which song is currently playing

### (L) About

1. Static page - html only? 
1. Consider making contact info configurable.
