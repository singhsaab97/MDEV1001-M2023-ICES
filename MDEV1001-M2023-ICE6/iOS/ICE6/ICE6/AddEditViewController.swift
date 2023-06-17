import UIKit
import CoreData

class AddEditViewController: UIViewController
{
    // UI References
    @IBOutlet weak var AddEditTitleLabel: UILabel!
    @IBOutlet weak var UpdateButton: UIButton!
    
    // Movie Fields
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var studioTextField: UITextField!
    @IBOutlet weak var genresTextField: UITextField!
    @IBOutlet weak var directorsTextField: UITextField!
    @IBOutlet weak var writersTextField: UITextField!
    @IBOutlet weak var actorsTextField: UITextField!
    @IBOutlet weak var yearTextField: UITextField!
    @IBOutlet weak var lengthTextField: UITextField!
    @IBOutlet weak var descriptionTextView: UITextView!
    @IBOutlet weak var mpaRatingTextField: UITextField!
    @IBOutlet weak var criticsRatingTextField: UITextField!
    
    var movie: Movie?
    var dataViewController: DataViewController?
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        if let movie = movie
        {
            // Editing existing movie
            titleTextField.text = movie.title
            studioTextField.text = movie.studio
            genresTextField.text = movie.genres
            directorsTextField.text = movie.directors
            writersTextField.text = movie.writers
            actorsTextField.text = movie.actors
            lengthTextField.text = "\(movie.length)"
            yearTextField.text = "\(movie.year)"
            descriptionTextView.text = movie.shortdescription
            mpaRatingTextField.text = movie.mparating
            criticsRatingTextField.text = "\(movie.criticsrating)"
        }
        else
        {
            AddEditTitleLabel.text = "Add Movie"
            UpdateButton.setTitle("Add", for: .normal)
        }
    }
    
    @IBAction func CancelButton_Pressed(_ sender: UIButton)
    {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func UpdateButton_Pressed(_ sender: UIButton)
    {
        // Retrieve the app delegate
        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else
        {
            return
        }

        // Retrieve the managed object context
        let context = appDelegate.persistentContainer.viewContext

        if let movie = movie
        {
            // Editing existing movie
            movie.title = titleTextField.text
            movie.studio = studioTextField.text
            movie.genres = genresTextField.text
            movie.directors = directorsTextField.text
            movie.writers = writersTextField.text
            movie.actors = actorsTextField.text
            movie.length = Int16(lengthTextField.text ?? "") ?? 0
            movie.year = Int16(yearTextField.text ?? "") ?? 0
            movie.shortdescription = descriptionTextView.text
            movie.mparating = mpaRatingTextField.text
            movie.criticsrating = Double(criticsRatingTextField.text ?? "") ?? 0.0
        } else {
            // Creating a new movie
            let newMovie = Movie(context: context)
            newMovie.title = titleTextField.text
            newMovie.studio = studioTextField.text
            newMovie.genres = genresTextField.text
            newMovie.directors = directorsTextField.text
            newMovie.writers = writersTextField.text
            newMovie.actors = actorsTextField.text
            newMovie.length = Int16(lengthTextField.text ?? "") ?? 0
            newMovie.year = Int16(yearTextField.text ?? "") ?? 0
            newMovie.shortdescription = descriptionTextView.text
            newMovie.mparating = mpaRatingTextField.text
            newMovie.criticsrating = Double(criticsRatingTextField.text ?? "") ?? 0.0
        }

        // Save the changes in the context
        do {
            try context.save()
            dataViewController?.fetchData()
            dismiss(animated: true, completion: nil)
        } catch {
            print("Failed to save data: \(error)")
        }
    }
    
}
