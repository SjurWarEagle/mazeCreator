import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MazeGenerationRcServiceService {
  public fileName: BehaviorSubject<string> = new BehaviorSubject<string>("");
  public mazeBase64?: string;
  public imgDataMazeWithSolutionBase64?: string;

  constructor(private http: HttpClient) {
  }

  public async generateMaze(file: File) {
    if (file) {
      this.fileName.next(file.name);
      const formData = new FormData();
      formData.append("sourceImage", file);
      let mazeData: any = await this.http.put("/api/generate/forWebWithSolution", formData).toPromise();
      this.mazeBase64='data:image/jpeg;base64,'+mazeData.imgDataMazeBase64;
      this.imgDataMazeWithSolutionBase64='data:image/jpeg;base64,'+mazeData.imgDataMazeWithSolutionBase64;
    } else {
      this.fileName.next('<none>');
    }
  }
}
