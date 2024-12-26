<?php
// Handle GET request to display users
$headers = getallheaders();
$authToken = $headers['Authorization'] ?? '';

// Load existing users from the JSON file
$users = json_decode(file_get_contents('users.json'), true);

foreach ($users as $user) {
    if ($user['auth_token'] == $authToken) {
        // Return all users data
        echo json_encode(['status' => 'success', 'data' => $users]);
        exit;
    }
}

echo json_encode(['status' => 'error', 'message' => 'Invalid or expired token.']);
