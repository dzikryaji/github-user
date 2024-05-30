# GitHub User App

GitHub User is an Android app that allows you to find GitHub profiles using the GitHub API. You can effortlessly search for profiles by entering a username and bookmark your favorite profiles for easy access later.

## Features

- **Search Profiles:** Find GitHub profiles by entering a username.
- **Bookmark Profiles:** Save and manage your favorite profiles for quick access.
- **Theme Management:** Customize the app's appearance by switching between themes.

## Technical Details

### GitHub API Integration

GitHub User utilizes Retrofit to fetch data from the GitHub API, ensuring efficient and reliable communication with GitHub's servers.

### Bookmark Functionality

For the bookmarking function, the app employs Room Database, allowing users to save and manage their favorite profiles locally on their phones. This ensures that bookmarked data is quickly accessible and remains available even without an internet connection.

### Android Architecture Components

GitHub User integrates Android Architecture Components like ViewModel and LiveData to enhance its performance and responsiveness.

- **ViewModel:** Manages UI-related data in a lifecycle-conscious way, ensuring data survives configuration changes such as screen rotations.
- **LiveData:** Provides a reactive approach to data handling, automatically updating the UI when data changes.

### Theme Management

GitHub User incorporates DataStore to manage and switch between themes. DataStore provides a robust way to store key-value pairs or typed objects, ensuring that theme preferences are saved and applied consistently.

## Screenshots

### Dark Theme
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/dzikryaji/github-user/blob/master/splash-dark.png?raw=true" alt="Splash Screen Dark" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/user-list-dark.png?raw=true" alt="Main Screen Dark" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/user-detail-dark.png?raw=true" alt="Detail Screen Dark" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/bookmark-dark.png?raw=true" alt="Bookmark Screen Dark" width="20%">
</div>

### Light Theme
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/dzikryaji/github-user/blob/master/splash-light.png?raw=true" alt="Splash Screen Light" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/user-list-light.png?raw=true" alt="Main Screen Light" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/user-detail-light.png?raw=true" alt="Detail Screen Light" width="20%">
  <img src="https://github.com/dzikryaji/github-user/blob/master/bookmark-light.png?raw=true" alt="Bookmark Screen Light" width="20%">
</div>
