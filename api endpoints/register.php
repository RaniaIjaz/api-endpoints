<?php
// Handle POST request to register a new user
$data = json_decode(file_get_contents('php://input'), true);

if (!isset($data['username'], $data['email'], $data['password'])) {
    echo json_encode(['status' => 'error', 'message' => 'All fields are required.']);
    exit;
}

// Load existing users from the JSON file
$users = json_decode(file_get_contents('users.json'), true);

// Check if the email already exists
foreach ($users as $user) {
    if ($user['email'] == $data['email']) {
        echo json_encode(['status' => 'error', 'message' => 'Email already in use.']);
        exit;
    }
}

// Hash the password
$hashedPassword = password_hash($data['password'], PASSWORD_DEFAULT);

// Create new user data
$newUser = [
    'username' => $data['username'],
    'email' => $data['email'],
    'password' => $hashedPassword,
    'auth_token' => null
];

// Add new user to the array
$users[] = $newUser;

// Save the updated users array to the JSON file
file_put_contents('users.json', json_encode($users));

echo json_encode(['status' => 'success', 'message' => 'User registered successfully.']);
